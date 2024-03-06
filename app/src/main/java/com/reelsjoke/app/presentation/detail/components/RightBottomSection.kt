package com.reelsjoke.app.presentation.detail.components

import android.graphics.Bitmap
import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.reelsjoke.app.R
import com.reelsjoke.app.domain.model.ScreenInfo


/**
 * Created by bedirhansaricayir on 20.01.2024.
 */

@Composable
fun RightBottomSection(
    modifier: Modifier = Modifier,
    screenInfo: ScreenInfo,
) {
    Column(
        modifier = modifier.padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconWithText(
            icon = if (screenInfo.isLiked) R.drawable.ic_filled_favorite else R.drawable.ic_outlined_favorite,
            text = screenInfo.likes,
            iconColor = if (screenInfo.isLiked) Color.Red else Color.White,
        )
        IconWithText(
            icon = R.drawable.ic_outlined_comment,
            text = screenInfo.commentCount,
            iconColor = Color.White,
        )
        IconWithText(icon = R.drawable.ic_dm, text = screenInfo.send, iconColor = Color.White)
        Icon(
            modifier = Modifier.padding(vertical = 8.dp),
            imageVector = Icons.Outlined.MoreVert,
            contentDescription = "",
            tint = Color.White
        )
        RoundedSoundImage(image = screenInfo.soundImage)
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
fun IconWithText(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int,
    iconColor: Color,
    text: String
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier
                .size(32.dp),
            painter = painterResource(id = icon),
            contentDescription = "",
            tint = iconColor
        )
        Text(
            text = text,
            color = Color.White,
            fontWeight = FontWeight.SemiBold,
            fontSize = 12.sp
        )
    }
}

@Composable
fun RoundedSoundImage(
    modifier: Modifier = Modifier,
    image: Bitmap?
) {
    AsyncImage(
        model = image,
        modifier = modifier
            .size(28.dp)
            .border(
                BorderStroke(1.dp,Color.White),
                shape = RoundedCornerShape(6.dp)
            )
            .clip(RoundedCornerShape(6.dp)),
        contentScale = ContentScale.Crop,
        contentDescription = null
    )
}