package com.reelsjoke.app.presentation.detail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.reelsjoke.app.R
import com.reelsjoke.app.core.extensions.noRippleClickable

@Composable
fun DetailScreenTopBar(
    modifier: Modifier = Modifier,
    onBackButtonClicked: () -> Unit,
    onDownloadButtonClicked: () -> Unit,
    onShareButtonClicked: () -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TopBarIconComponent(
            imageVector = Icons.Default.KeyboardArrowLeft,
            onIconClicked = onBackButtonClicked
        )
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            TopBarIconComponent(
                iconDrawable = R.drawable.ic_download,
                onIconClicked = onDownloadButtonClicked
            )
            Spacer(modifier = Modifier.width(4.dp))
            TopBarIconComponent(
                imageVector = Icons.Default.Share,
                onIconClicked = onShareButtonClicked
            )
        }

    }
}

@Composable
fun TopBarIconComponent(
    modifier: Modifier = Modifier,
    backgroundSize: Dp = 36.dp,
    backgroundColor: Color = MaterialTheme.colorScheme.onBackground,
    iconSize: Dp = 24.dp,
    iconColor: Color = MaterialTheme.colorScheme.background,
    imageVector: ImageVector? = null,
    iconDrawable: Int? = null,
    onIconClicked: () -> Unit
) {
    Box(
        modifier = modifier
            .size(backgroundSize)
            .clip(CircleShape)
            .background(
                color = backgroundColor.copy(alpha = 0.6f),
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        imageVector?.let { icon ->
            Icon(
                modifier = Modifier
                    .size(iconSize)
                    .noRippleClickable { onIconClicked() },
                imageVector = icon,
                contentDescription = "Back Button",
                tint = iconColor
            )
        } ?: run {
            Icon(
                modifier = Modifier
                    .size(iconSize)
                    .noRippleClickable { onIconClicked() },
                painter = painterResource(id = iconDrawable!!),
                contentDescription = "Back Button",
                tint = iconColor
            )
        }

    }
}