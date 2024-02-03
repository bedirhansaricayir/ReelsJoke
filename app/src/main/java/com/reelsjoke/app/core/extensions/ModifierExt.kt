package com.reelsjoke.app.core.extensions

import android.annotation.SuppressLint
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


/**
 * Created by bedirhansaricayir on 28.01.2024.
 */

@SuppressLint("ModifierFactoryUnreferencedReceiver")
fun Modifier.noRippleClickable(enabled: Boolean = true, onClick: () -> Unit): Modifier = composed {
    clickable(
        indication = null,
        enabled = enabled,
        interactionSource = remember { MutableInteractionSource() }) {
        onClick.invoke()
    }
}

private val colors = listOf(
    Color(0xFF6FC04F),
    Color(0xFFFDCB5C),
    Color(0xFFFC8033),
    Color(0xFFEF4956),
    Color(0xFF3A96F0),
    Color(0xFFEF4956),
    Color(0xFFFC8033),
    Color(0xFFFDCB5C),
    Color(0xFF6FC04F),
)

@Composable
fun Modifier.animatedBorder(
    borderColors: List<Color> = colors,
    shape: Shape = ButtonDefaults.shape,
    borderWidth: Dp = 1.dp,
    animationDurationInMillis: Int = 1_500,
    infiniteRepeatable: Boolean
): Modifier {

    val brush = Brush.sweepGradient(borderColors)
    val infiniteTransition = rememberInfiniteTransition(label = "animatedBorder")

    val angleInfinite: Float by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec =
        infiniteRepeatable(
            animation = tween(
                durationMillis = animationDurationInMillis,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "angleAnimation"
    )

    return this
        .clip(shape)
        .padding(borderWidth)
        .drawWithContent {
            rotate(degrees = if (infiniteRepeatable) angleInfinite else 0f, block = {
                drawCircle(
                    brush = brush,
                    radius = size.width,
                    blendMode = BlendMode.SrcIn,
                )
            })
            drawContent()
        }
        .background(color = MaterialTheme.colorScheme.background, shape = shape)
}