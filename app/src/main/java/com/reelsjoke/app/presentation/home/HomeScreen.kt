package com.reelsjoke.app.presentation.home

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.reelsjoke.app.R
import com.reelsjoke.app.core.Contants.Companion.BASE_CREATE_COUNT
import com.reelsjoke.app.core.components.BalloonComponent
import com.reelsjoke.app.core.extensions.bouncingClickable
import com.reelsjoke.app.core.extensions.noRippleClickable
import com.reelsjoke.app.domain.model.ScreenInfo


/**
 * Created by bedirhansaricayir on 20.01.2024.
 */

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.HomeScreen(
    homeUIState: HomeScreenUIState,
    animatedVisibilityScope: AnimatedVisibilityScope,
    onEvent: (HomeScreenUIEvent) -> Unit,
) {
    HomeScreenContent(
        homeUIState = homeUIState,
        animatedVisibilityScope = animatedVisibilityScope,
        onEvent = onEvent,
    )
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.HomeScreenContent(
    homeUIState: HomeScreenUIState,
    animatedVisibilityScope: AnimatedVisibilityScope,
    onEvent: (HomeScreenUIEvent) -> Unit,
) {
    when (homeUIState) {
        is HomeScreenUIState.Error -> ErrorScreen(onClicked = { onEvent(HomeScreenUIEvent.OnRefreshButtonClicked) })
        is HomeScreenUIState.Success -> HomeScreenWrapper(homeUIState = homeUIState,animatedVisibilityScope = animatedVisibilityScope, onEvent = onEvent)

        is HomeScreenUIState.Loading -> LoadingScreen()
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.HomeScreenWrapper(
    homeUIState: HomeScreenUIState.Success,
    animatedVisibilityScope: AnimatedVisibilityScope,
    onEvent: (HomeScreenUIEvent) -> Unit,
) {
    Scaffold(
        floatingActionButton = {
            FloatingButtonWithBalloon(
                showBalloon = homeUIState.isBalloonShown
            ) {
                if (!homeUIState.isBalloonShown) {
                    onEvent(HomeScreenUIEvent.OnBalloonShown)
                }
                homeUIState.let { state ->
                    state.takeIf { !state.isPremium && state.data?.size!! >= BASE_CREATE_COUNT }
                        ?.run { onEvent(HomeScreenUIEvent.OnReachTheLimit) }
                        ?: onEvent(HomeScreenUIEvent.OnFabClicked)
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HomeScreen(
                data = homeUIState.data.orEmpty(),
                animatedVisibilityScope = animatedVisibilityScope,
                onItemClicked = { item ->
                    onEvent(HomeScreenUIEvent.OnItemClicked(item))
                },
                onSettingsClicked = {
                    onEvent(HomeScreenUIEvent.OnSettingsClicked)
                }
            )
        }

    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.HomeScreen(
    data: List<ScreenInfo>?,
    animatedVisibilityScope: AnimatedVisibilityScope,
    onItemClicked: (ScreenInfo) -> Unit,
    onSettingsClicked: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (data?.isEmpty() == true) EmptyHomeScreen(onClick = onSettingsClicked)
        else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
            ) {
                HomeScreenTopBar(
                    title = "Reels",
                    onClick = onSettingsClicked
                )
                LazyVerticalGrid(
                    modifier = Modifier
                        .fillMaxSize(),
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(data.orEmpty()) { screenInfo ->
                        HomeScreenItem(
                            screenInfo = screenInfo,
                            animatedVisibilityScope = animatedVisibilityScope,
                            onClick = onItemClicked
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun EmptyHomeScreen(
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier.size(80.dp),
                painter = painterResource(id = R.drawable.reels_icon),
                contentDescription = "Reels Icon",
                colorFilter = ColorFilter.tint(color = Color.Gray)
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "No content here yet",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Gray
            )
        }
        SettingsButton(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(vertical = 16.dp, horizontal = 8.dp),
            onClick = onClick
        )
    }
}

@Composable
fun HomeScreenTopBar(
    modifier: Modifier = Modifier,
    title: String,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier,
            text = title,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        SettingsButton(
            onClick = onClick
        )
    }
}

@Composable
fun SettingsButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Icon(
        modifier = modifier
            .size(32.dp)
            .padding(end = 8.dp)
            .bouncingClickable(onClick = onClick),
        painter = painterResource(id = R.drawable.ic_settings),
        contentDescription = "Settings Icon",
        tint = MaterialTheme.colorScheme.onBackground
    )
}

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorScreen(
    onClicked: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.error_title),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Gray
            )
            Button(
                onClick = { onClicked.invoke() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = Color.White
                )
            ) {
                Icon(
                    modifier = Modifier.padding(end = 8.dp),
                    imageVector = Icons.Default.Refresh,
                    contentDescription = stringResource(id = R.string.refresh_button_title)
                )
                Text(
                    text = stringResource(id = R.string.refresh_button),
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold
                )

            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.HomeScreenItem(
    screenInfo: ScreenInfo,
    animatedVisibilityScope: AnimatedVisibilityScope,
    onClick: (ScreenInfo) -> Unit
) {
    Card(
        modifier = Modifier
            .height(300.dp)
            .width(200.dp)
            .noRippleClickable { onClick(screenInfo) },
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                modifier = Modifier
                    .sharedElement(
                        state = rememberSharedContentState(key = "image/${screenInfo.backgroundImage}"),
                        animatedVisibilityScope = animatedVisibilityScope
                    )
                    .fillMaxSize()
                    .clip(RoundedCornerShape(8.dp)),
                model = screenInfo.backgroundImage,
                contentDescription = "Image",
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
fun FloatingButtonWithBalloon(
    showBalloon: Boolean,
    onClick: () -> Unit
) {
    if (!showBalloon)
        BalloonComponent {
            FloatingButton(onClick = onClick)
        }
    else
        FloatingButton(onClick = onClick)
}


@Composable
fun FloatingButton(
    onClick: () -> Unit
) {

    FloatingActionButton(
        onClick = onClick,
        containerColor = Color(0xFF24A0ED),
        contentColor = Color.White,
        shape = CircleShape
    ) {
        Icon(
            painter = painterResource(id = R.drawable.add_reels_icon),
            contentDescription = "Camera Icon"
        )
    }
}