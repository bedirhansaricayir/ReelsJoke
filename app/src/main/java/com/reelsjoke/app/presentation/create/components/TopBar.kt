package com.reelsjoke.app.presentation.create.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.reelsjoke.app.core.extensions.animatedBorder
import com.reelsjoke.app.core.extensions.noRippleClickable

/**
 * Created by bedirhansaricayir on 26.04.2024.
 */

@Composable
fun CreateScreenTopBar(
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit,
    onButtonClick: () -> Unit,
    infiniteRepeatable: Boolean
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.noRippleClickable { navigateUp() },
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Back Button",
            tint = MaterialTheme.colorScheme.onBackground
        )
        ExportButton(onClick = onButtonClick, infiniteRepeatable = infiniteRepeatable)
    }
}

@Composable
fun ExportButton(
    onClick: () -> Unit,
    infiniteRepeatable: Boolean
) {
    Button(
        modifier = Modifier
            .animatedBorder(infiniteRepeatable = infiniteRepeatable)
            .height(40.dp)
            .width(120.dp),
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.background),
        onClick = onClick
    ) {
        Text(
            modifier = Modifier,
            text = if (infiniteRepeatable) "Exporting..." else "Export",
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 10.sp,
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.W500
        )
    }
}