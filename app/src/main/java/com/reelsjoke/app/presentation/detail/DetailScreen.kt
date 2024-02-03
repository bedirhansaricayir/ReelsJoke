package com.reelsjoke.app.presentation.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import coil.compose.AsyncImage
import com.reelsjoke.app.domain.model.ScreenInfo


/**
 * Created by bedirhansaricayir on 20.01.2024.
 */


@Composable
fun DetailScreen(screenInfo: ScreenInfo?) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black)
    ) {
        screenInfo?.let {
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
                screenInfo = screenInfo
            )
        }


    }
}






