package com.reelsjoke.app.presentation.create.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

/**
 * Created by bedirhansaricayir on 26.04.2024.
 */

@Composable
fun CustomTextField(
    value: String,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
    hintText: String = "Write a description and add hashtags...",
    singleLine: Boolean = false,
    keyboardOptions: KeyboardOptions
) {
    BasicTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChanged,
        textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Start, fontSize = 12.sp, color = Color.Black.copy(0.8f)),
        keyboardOptions = keyboardOptions,
        maxLines = if (singleLine) 1 else Int.MAX_VALUE,
        decorationBox = { innerTextField ->
            Box(modifier = modifier) {
                if (value.isEmpty()) {
                    Text(
                        modifier = Modifier,
                        textAlign = TextAlign.Start,
                        text = hintText,
                        fontSize = 12.sp,
                        color = if (isSystemInDarkTheme()) Color.LightGray else Color.Gray
                    )
                }
                innerTextField()
            }
        }
    )

}