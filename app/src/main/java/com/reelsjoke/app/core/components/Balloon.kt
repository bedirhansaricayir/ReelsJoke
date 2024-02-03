package com.reelsjoke.app.core.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.skydoves.balloon.ArrowPositionRules
import com.skydoves.balloon.BalloonAnimation
import com.skydoves.balloon.BalloonHighlightAnimation
import com.skydoves.balloon.BalloonSizeSpec
import com.skydoves.balloon.compose.Balloon
import com.skydoves.balloon.compose.BalloonWindow
import com.skydoves.balloon.compose.rememberBalloonBuilder


/**
 * Created by bedirhansaricayir on 3.02.2024.
 */

private const val defaultOverlayColor = 0.7f
private const val defaultArrowSize = 20
private const val defaultArrowPosition = 0.5f
private const val defaultPadding = 12
private const val defaultHorizontalMargin = 12
private const val defaultVerticalMargin = 18
private const val defaultRadius = 8f
private val defaultAnimation = BalloonAnimation.ELASTIC
private val bgColor = Color(0xFF24A0ED).toArgb()

@Composable
fun BalloonComponent(
    content: @Composable (BalloonWindow?) -> Unit
) {
    var balloonWindow: BalloonWindow? by remember { mutableStateOf(null) }

    val builder = rememberBalloonBuilder {
        backgroundColor = bgColor
        isVisibleOverlay = true
        overlayColor = Color.Black.copy(alpha = defaultOverlayColor).toArgb()
        overlayPadding = 32f
        dismissWhenClicked = true
        arrowSize = defaultArrowSize
        arrowPosition = defaultArrowPosition
        arrowPositionRules = ArrowPositionRules.ALIGN_ANCHOR
        width = BalloonSizeSpec.WRAP
        height = BalloonSizeSpec.WRAP
        setPadding(defaultPadding)
        setMarginHorizontal(defaultHorizontalMargin)
        setMarginVertical(defaultVerticalMargin)
        cornerRadius = defaultRadius
        balloonAnimation = defaultAnimation
        balloonHighlightAnimation = BalloonHighlightAnimation.HEARTBEAT


    }

    Balloon(
        builder = builder,
        onBalloonWindowInitialized = { balloonWindow = it },
        onComposedAnchor = { balloonWindow?.showAlignTop() },
        balloonContent = {
            Text(
                text = "Now create a reels",
                textAlign = TextAlign.Center,
                color = Color.White,
                fontSize = 14.sp
            )
        }
    ) {
        content(balloonWindow)
    }
}