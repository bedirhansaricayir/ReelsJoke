package com.reelsjoke.app.presentation.detail

import android.graphics.Bitmap
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.layer.drawLayer
import androidx.compose.ui.graphics.rememberGraphicsLayer
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.reelsjoke.app.presentation.detail.components.DetailScreenTopBar
import com.reelsjoke.app.presentation.detail.components.LeftBottomSection
import com.reelsjoke.app.presentation.detail.components.RightBottomSection
import kotlinx.coroutines.launch


/**
 * Created by bedirhansaricayir on 20.01.2024.
 */

@Composable
fun DetailScreen(
    state: DetailScreenUIState,
    onEvent: (DetailScreenUIEvent) -> Unit
) {
    DetailScreenContent(
        state = state,
        onBackButtonClicked = { onEvent(DetailScreenUIEvent.OnBackButtonClicked) },
        onDownloadButtonClicked = { onEvent(DetailScreenUIEvent.OnDownloadButtonClicked(it)) },
        onShareButtonClicked = { onEvent(DetailScreenUIEvent.OnShareButtonClicked(it)) }
    )
}

@Composable
fun DetailScreenContent(
    state: DetailScreenUIState,
    onBackButtonClicked: () -> Unit,
    onDownloadButtonClicked: (Bitmap) -> Unit,
    onShareButtonClicked: (Bitmap) -> Unit
) {
    val graphicsLayer = rememberGraphicsLayer()
    val coroutineScope = rememberCoroutineScope()

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
                    modifier = Modifier.fillMaxSize(),
                    model = screenInfo.backgroundImage,
                    contentDescription = "Reels Image"
                )

                LeftBottomSection(
                    modifier = Modifier.align(Alignment.BottomStart),
                    screenInfo = screenInfo,
                )
                RightBottomSection(
                    modifier = Modifier.align(Alignment.BottomEnd),
                    screenInfo = screenInfo,
                )
            }
        }
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



