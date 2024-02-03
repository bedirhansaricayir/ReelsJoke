package com.reelsjoke.app.core

import android.view.MotionEvent
import androidx.annotation.DrawableRes
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.reelsjoke.app.core.extensions.animatedBorder
import com.reelsjoke.app.domain.model.QuestionType


/**
 * Created by bedirhansaricayir on 27.01.2024.
 */

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AnimatedIcon(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int
) {
    val selected = remember { mutableStateOf(false) }
    val scale = animateFloatAsState(if (selected.value) 1.5f else 1f, label = "")

    Icon(
        modifier = modifier
            .scale(scale.value)
            .size(32.dp)
            .pointerInteropFilter {
                when (it.action) {
                    MotionEvent.ACTION_DOWN -> selected.value = true
                    MotionEvent.ACTION_UP -> selected.value = false
                }
                true
            },
        painter = painterResource(id = icon),
        contentDescription = "Icon",
        tint = Color.DarkGray
    )
}

@Composable
fun ExportButton(
    onClick: () -> Unit,
    infiniteRepeatable: Boolean
) {
    Button(
        modifier = Modifier
            .animatedBorder(shape = CircleShape, infiniteRepeatable = infiniteRepeatable)
            .height(40.dp)
            .width(100.dp),
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray),
        onClick = onClick
    ) {
        Text(
            modifier = Modifier,
            text = if (infiniteRepeatable) "Exporting..." else "Export",
            color = Color.LightGray,
            fontSize = 10.sp,
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.W500
        )
    }
}

@Composable
fun CustomInputField(
    modifier: Modifier = Modifier,
    questionType: QuestionType,
    value: String,
    imeAction: ImeAction,
    onValueChanged: (String) -> Unit
) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = { onValueChanged(it) },
        label = {
            Text(
                text = questionType.question,
                fontSize = 12.sp
            )
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = imeAction)
    )
}

@Composable
fun RoundedCornerBox(
    modifier: Modifier = Modifier,
    borderStroke: Dp = 1.dp,
    borderColor: Color = Color.Black,
    borderShape: Dp = 8.dp,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .border(
                border = BorderStroke(width = borderStroke, color = borderColor),
                shape = RoundedCornerShape(borderShape)
            )
            .animateContentSize()
    ) {
        content()
    }
}

