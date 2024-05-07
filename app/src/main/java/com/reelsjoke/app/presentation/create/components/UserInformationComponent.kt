package com.reelsjoke.app.presentation.create.components

import android.graphics.Bitmap
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.reelsjoke.app.R
import com.reelsjoke.app.core.extensions.noRippleClickable

enum class UserInformationCategory(val title: String) {
    IMAGE("Profile image"),
    USERNAME("Username"),
    VERIFIED("Verified account"),
    FOLLOWED("Is followed")
}


@Composable
fun UserInformationComponent(
    modifier: Modifier = Modifier,
    category: UserInformationCategory,
    endIcon: @Composable () -> Unit,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .clickable(role = Role.Button) { onClick() }
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = category.title,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.weight(1f),

            )
        when(category) {
            UserInformationCategory.IMAGE -> endIcon()
            UserInformationCategory.USERNAME -> EndIconGoForward()
            UserInformationCategory.VERIFIED -> endIcon()
            UserInformationCategory.FOLLOWED -> endIcon()
        }
    }
}

@Composable
fun ForwardIconWithImage(
    modifier: Modifier = Modifier,
    image: Bitmap? = null
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        image?.let {
            AnimatedVisibility(
                visible = true,
                enter = fadeIn() + slideInHorizontally(
                    animationSpec = tween(durationMillis = 5000),
                    initialOffsetX = {-it}
                ),
                exit = fadeOut() + slideOutHorizontally(
                    animationSpec = tween(5000),
                    targetOffsetX = {it}
                )
            ) {
                AsyncImage(
                    modifier = Modifier
                        .size(20.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    model = it,
                    contentDescription = "Selected Image",
                    contentScale = ContentScale.Crop,
                )
            }
        }

        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = "Go Forward",
            tint = Color.Gray
        )
    }

}

@Composable
fun EndIconGoForward() {
    Icon(
        imageVector = Icons.Default.KeyboardArrowRight,
        contentDescription = "Go Forward",
        tint = Color.Gray
    )
}

@Composable
fun EndIconSwitch(
    modifier: Modifier = Modifier,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    AnimatedContent(
        targetState = checked,
        label = ""
    ) {
        Text(
            modifier = modifier.noRippleClickable { onCheckedChange(!checked) },
            text = if (it) "Followed" else "Follow",
            fontSize = 12.sp,
            color = if (isSystemInDarkTheme()) Color.LightGray else Color.Gray
        )
    }
}

@Composable
fun EndIconVerified(
    modifier: Modifier = Modifier,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = modifier.noRippleClickable { onCheckedChange(!checked) },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        AnimatedContent(targetState = checked, label = "") {

            Text(
                textAlign = TextAlign.Start,
                text = if (it) "Verified" else "Unverified",
                fontSize = 12.sp,
                color = if (isSystemInDarkTheme()) Color.LightGray else Color.Gray
            )
        }

        Icon(
            painter = painterResource(id = R.drawable.verified_badge),
            contentDescription = "",
            tint = if (checked) Color.Unspecified else Color.Gray
        )
    }
}