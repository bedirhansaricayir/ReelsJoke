package com.reelsjoke.app.presentation.detail

import android.graphics.Bitmap
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.layer.drawLayer
import androidx.compose.ui.graphics.rememberGraphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.reelsjoke.app.presentation.detail.components.DetailScreenTopBar
import com.reelsjoke.app.presentation.detail.components.LeftBottomSection
import com.reelsjoke.app.presentation.detail.components.RightBottomSection
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds


/**
 * Created by bedirhansaricayir on 20.01.2024.
 */

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.DetailScreen(
    state: DetailScreenUIState,
    animatedVisibilityScope: AnimatedVisibilityScope,
    onEvent: (DetailScreenUIEvent) -> Unit
) {
    DetailScreenContent(
        state = state,
        animatedVisibilityScope = animatedVisibilityScope,
        onBackButtonClicked = { onEvent(DetailScreenUIEvent.OnBackButtonClicked) },
        onDownloadButtonClicked = { onEvent(DetailScreenUIEvent.OnDownloadButtonClicked(it)) },
        onShareButtonClicked = { onEvent(DetailScreenUIEvent.OnShareButtonClicked(it)) }
    )
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.DetailScreenContent(
    state: DetailScreenUIState,
    animatedVisibilityScope: AnimatedVisibilityScope,
    onBackButtonClicked: () -> Unit,
    onDownloadButtonClicked: (Bitmap) -> Unit,
    onShareButtonClicked: (Bitmap) -> Unit
) {
    val graphicsLayer = rememberGraphicsLayer()
    val coroutineScope = rememberCoroutineScope()
    var visible by remember { mutableStateOf(false) }
    val density = LocalDensity.current

    LaunchedEffect(Unit) {
        delay(0.3.seconds)
        visible = true
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Black)
                .drawWithContent {
                    graphicsLayer.record {
                        this@drawWithContent.drawContent()
                    }
                    drawLayer(graphicsLayer)
                }
        ) {
            state.screenInfo?.let { screenInfo ->
                AsyncImage(
                    modifier = Modifier
                        .sharedElement(
                            state = rememberSharedContentState(key = "image/${screenInfo.backgroundImage}"),
                            animatedVisibilityScope = animatedVisibilityScope
                        )
                        .fillMaxSize(),
                    model = screenInfo.backgroundImage,
                    contentDescription = "Reels Image",
                )


                AnimatedVisibility(
                    modifier = Modifier.align(Alignment.BottomStart),
                    visible = visible,
                    enter = slideInHorizontally(
                        animationSpec = tween(500)
                    ) {
                        with(density) { 40.dp.roundToPx() }
                    } + expandHorizontally(
                        animationSpec = tween(500),
                        expandFrom = Alignment.End
                    ) + fadeIn(
                        initialAlpha = 0.3f
                    ),
                    exit = slideOutHorizontally {
                        with(density) { -40.dp.roundToPx() }
                    } + shrinkHorizontally() + fadeOut()
                ) {
                    LeftBottomSection(
                        modifier = Modifier.align(Alignment.BottomStart),
                        screenInfo = screenInfo,
                    )
                }

                AnimatedVisibility(
                    modifier = Modifier.align(Alignment.BottomEnd),
                    visible = visible,
                    enter = slideInHorizontally(
                        animationSpec = tween(500)
                    ) {
                        with(density) { 40.dp.roundToPx() }
                    } + expandHorizontally(
                        animationSpec = tween(500),
                        expandFrom = Alignment.End
                    ) + fadeIn(
                        initialAlpha = 0.3f
                    ),
                    exit = slideOutHorizontally {
                        with(density) { 40.dp.roundToPx() }
                    } + shrinkHorizontally() + fadeOut()
                ) {
                    RightBottomSection(
                        modifier = Modifier.align(Alignment.BottomEnd),
                        screenInfo = screenInfo,
                    )
                }


            }
        }
        AnimatedVisibility(
            visible = visible,
            enter = slideInVertically(
                animationSpec = tween(500)
            ) {
                with(density) { -40.dp.roundToPx() }
            } + expandVertically(
                animationSpec = tween(500),
                expandFrom = Alignment.Top
            ) + fadeIn(
                initialAlpha = 0.3f
            ),
            exit = slideOutVertically() + shrinkVertically() + fadeOut()

        ) {
            DetailScreenTopBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
                    .statusBarsPadding(),
                onBackButtonClicked = onBackButtonClicked,
                onDownloadButtonClicked = {
                    coroutineScope.launch {
                        val bitmap = graphicsLayer.toImageBitmap()
                        onDownloadButtonClicked(bitmap.asAndroidBitmap())
                    }
                },
                onShareButtonClicked = {
                    coroutineScope.launch {
                        val bitmap = graphicsLayer.toImageBitmap()
                        onShareButtonClicked(bitmap.asAndroidBitmap())
                    }
                }
            )
        }

    }
}



