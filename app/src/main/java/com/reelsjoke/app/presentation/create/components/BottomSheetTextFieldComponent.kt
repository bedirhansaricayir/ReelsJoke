package com.reelsjoke.app.presentation.create.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun BottomSheetTextFieldComponent(
    modifier: Modifier = Modifier,
    name: String = "Ses",
    onNameChanged: (String) -> Unit = {},
    hintText: String,
    titleText: String,
    singleLine: Boolean = true,
    shouldFocusRequest: Boolean = true,
    imeAction: ImeAction = ImeAction.Done
) {
    val focusRequester = remember {
        FocusRequester()
    }

    LaunchedEffect(Unit) {
        delay(100)
        if (shouldFocusRequest) focusRequester.requestFocus()
    }

    Column(
        modifier = Modifier.fillMaxWidth().padding(8.dp)
    ) {
        Text(
            modifier = Modifier,
            textAlign = TextAlign.Start,
            text = titleText,
            fontSize = 12.sp,
            color = if (isSystemInDarkTheme()) Color.LightGray else Color.Gray
        )
        CustomTextField(
            modifier = modifier.focusRequester(focusRequester),
            value = name,
            onValueChanged = onNameChanged,
            hintText = hintText,
            singleLine = singleLine,
            keyboardOptions = KeyboardOptions(
                imeAction = imeAction
            )
        )
        HorizontalDivider(thickness = 0.5.dp)
    }

}