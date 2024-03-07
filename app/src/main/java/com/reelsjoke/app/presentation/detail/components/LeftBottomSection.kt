package com.reelsjoke.app.presentation.detail.components

import android.graphics.Bitmap
import androidx.annotation.DrawableRes
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidthIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.reelsjoke.app.R
import com.reelsjoke.app.domain.model.ScreenInfo


/**
 * Created by bedirhansaricayir on 20.01.2024.
 */

@Composable
fun LeftBottomSection(
    modifier: Modifier = Modifier,
    screenInfo: ScreenInfo
) {
    Column(
        modifier = modifier.padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        UserSection(screenInfo = screenInfo)
        VideoDescription(
            modifier = Modifier.padding(8.dp),
            screenInfo = screenInfo
        )

        BottomDetailTrackBarItems(
            modifier = Modifier,
            screenInfo = screenInfo
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
fun UserSection(
    modifier: Modifier = Modifier,
    screenInfo: ScreenInfo
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        UserImage(image = screenInfo.userImage)
        UserTitle(title = screenInfo.userTitle)
        FollowButton(isFollowed = screenInfo.isUserFollowed)
    }
}

@Composable
fun UserImage(
    modifier: Modifier = Modifier,
    image: Bitmap?
) {
    AsyncImage(
        modifier = modifier
            .size(40.dp)
            .clip(CircleShape),
        model = image,
        contentScale = ContentScale.Crop,
        contentDescription = "Circular User Image",
    )
}

@Composable
fun UserTitle(
    modifier: Modifier = Modifier,
    title: String
) {
    Text(
        modifier = modifier,
        text = title,
        color = Color.White,
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun FollowButton(
    modifier: Modifier = Modifier,
    isFollowed: Boolean
) {
    Text(
        text = if (isFollowed) "Following" else "Follow",
        color = Color.White,
        modifier = modifier
            .border(
                BorderStroke(1.dp, Color.White),
                RoundedCornerShape(6.dp),
            )
            .padding(horizontal = 15.dp, vertical = 2.dp),
        fontSize = 14.sp
    )
}

@Composable
fun VideoDescription(
    modifier: Modifier = Modifier,
    screenInfo: ScreenInfo
) {
    val scrollState = rememberScrollState()
    var expandedDesc by remember { mutableStateOf(false) }
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp

    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .widthIn(max = (screenWidth / 1.3).dp)
    ) {
        Text(
            text = screenInfo.description,
            fontSize = 12.sp,
            maxLines = if (expandedDesc) Int.MAX_VALUE else 1,
            color = Color.White,
            overflow = TextOverflow.Ellipsis,
            modifier = modifier
                .clickable {
                    expandedDesc = !expandedDesc
                }
                .animateContentSize()
        )
    }

}

@Composable
fun BottomDetailTrackBarItems(
    modifier: Modifier = Modifier,
    screenInfo: ScreenInfo,
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    val soundTrackDp =
        if (screenInfo.location != null && screenInfo.taggedPeople != null)
            (screenWidth / 3).dp else if (screenInfo.location != null || screenInfo.taggedPeople != null)
            (screenWidth / 2).dp else
            (screenWidth / 1.5).dp

    Row(
        modifier = modifier.requiredWidthIn(max = (screenWidth / 1.3).dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        DetailTrackBar(
            modifier = Modifier
                .widthIn(max = soundTrackDp),
            icon = R.drawable.ic_music,
            text = screenInfo.description
        )
        Spacer(modifier = Modifier.width(8.dp))
        screenInfo.location?.let { location ->
            if (location.isNotEmpty()) {
                DetailTrackBar(
                    modifier = Modifier
                        .widthIn(max = 100.dp),
                    icon = R.drawable.ic_location,
                    text = location
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
        screenInfo.taggedPeople?.let { taggedPeople ->
            /*if (taggedPeople.size == 1) {
                DetailTrackBar(
                    modifier = Modifier
                        .widthIn(max = 100.dp),
                    icon = R.drawable.ic_person,
                    text = taggedPeople[0]
                )
            }*/
            if (taggedPeople.isNotEmpty()) {
                DetailTrackBar(
                    modifier = Modifier
                        .widthIn(max = 100.dp),
                    icon = R.drawable.ic_person,
                    text = taggedPeople
                )
                Spacer(modifier = Modifier.width(8.dp))
            }

        }
    }
}


@OptIn(ExperimentalFoundationApi::class, ExperimentalFoundationApi::class)
@Composable
fun DetailTrackBar(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int,
    text: String
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent,
        ),
        border = BorderStroke(1.dp, Gray.copy(0.5f))
    ) {
        Row(
            modifier = Modifier
                .background(Color.Black.copy(0.3f))
                .padding(horizontal = 10.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = painterResource(id = icon),
                contentDescription = "music",
                modifier = Modifier.size(18.dp),
            )
            Spacer(modifier = Modifier.width(2.dp))

            Text(
                maxLines = 1,
                modifier = Modifier.basicMarquee(),
                text = text,
                color = Color.White,
                fontSize = 12.sp,
            )
        }
    }
}