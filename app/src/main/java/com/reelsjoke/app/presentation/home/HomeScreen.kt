package com.reelsjoke.app.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.reelsjoke.app.R
import com.reelsjoke.app.core.components.BalloonComponent
import com.reelsjoke.app.core.extensions.noRippleClickable
import com.reelsjoke.app.domain.model.ScreenInfo


/**
 * Created by bedirhansaricayir on 20.01.2024.
 */

@Composable
fun HomeScreen(
    homeUIState: HomeScreenUIState,
    onEvent: (HomeScreenEvent) -> Unit,
) {
    HomeScreenContent(
        homeUIState = homeUIState,
        onEvent = onEvent,
    )
}

@Composable
fun HomeScreenContent(
    homeUIState: HomeScreenUIState,
    onEvent: (HomeScreenEvent) -> Unit,
) {
    var balloonState by remember { mutableStateOf(false) }
    Scaffold(
        floatingActionButton = {
            FloatingButtonWithBalloon(
                showBalloon = balloonState
            ) {
                onEvent(HomeScreenEvent.OnFabClicked)
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (homeUIState) {
                is HomeScreenUIState.Error -> {}
                is HomeScreenUIState.Success -> {
                    HomeScreen(
                        data = homeUIState.data.orEmpty(),
                        onClick = { item ->
                            onEvent(HomeScreenEvent.OnItemClicked(item))
                        }
                    )
                    balloonState = homeUIState.showBalloon
                }


                is HomeScreenUIState.Loading -> LoadingScreen()
            }
        }

    }
}

@Composable
fun HomeScreen(
    data: List<ScreenInfo>?,
    onClick: (ScreenInfo) -> Unit
) {
    if (data?.isEmpty() == true) EmptyHomeScreen()
    else {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 16.dp),
                text = "Reels",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
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
                        onClick = onClick
                    )
                }
            }
        }
    }

}

@Composable
fun EmptyHomeScreen() {
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
fun HomeScreenItem(
    screenInfo: ScreenInfo,
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
                .fillMaxSize()
                .clip(RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
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