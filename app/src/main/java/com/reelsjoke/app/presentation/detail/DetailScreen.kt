package com.reelsjoke.app.presentation.detail

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ExperimentalComposeApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.reelsjoke.app.R
import com.reelsjoke.app.core.extensions.noRippleClickable
import com.reelsjoke.app.presentation.detail.components.DetailScreenTopBar
import com.reelsjoke.app.presentation.detail.components.LeftBottomSection
import com.reelsjoke.app.presentation.detail.components.RightBottomSection
import dev.shreyaspatil.capturable.capturable
import dev.shreyaspatil.capturable.controller.rememberCaptureController


/**
 * Created by bedirhansaricayir on 20.01.2024.
 */

@Composable
fun DetailScreen(
    state: DetailScreenUIState,
    onEvent: (DetailScreenUIEvent) -> Unit
) {
    DetailScreenContent(
        state = state,
        onBackButtonClicked = { onEvent(DetailScreenUIEvent.OnBackButtonClicked) },
        onDownloadButtonClicked = { onEvent(DetailScreenUIEvent.OnDownloadButtonClicked(it)) },
        onShareButtonClicked = { onEvent(DetailScreenUIEvent.OnShareButtonClicked(it)) }
    )
}

@OptIn(ExperimentalComposeApi::class, ExperimentalComposeUiApi::class)
@Composable
fun DetailScreenContent(
    state: DetailScreenUIState,
    onBackButtonClicked: () -> Unit,
    onDownloadButtonClicked: (Bitmap) -> Unit,
    onShareButtonClicked: (Bitmap) -> Unit
) {
    val captureController = rememberCaptureController()
    var convertToBitmap by remember { mutableStateOf(false) }
    var createdBitmap by remember { mutableStateOf<Bitmap?>(null) }


    LaunchedEffect(key1 = convertToBitmap) {
        if (convertToBitmap) {
            val bitmapAsync = captureController.captureAsync()
            try {
                val bitmap = bitmapAsync.await()
                createdBitmap = bitmap.asAndroidBitmap()

            } catch (error: Throwable) {
                Log.d("DetailScreenError",error.toString())
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Black)
                .capturable(captureController)

        ) {
            state.screenInfo?.let { screenInfo ->
                AsyncImage(
                    modifier = Modifier.fillMaxSize(),
                    model = screenInfo.backgroundImage,
                    contentDescription = "Reels Image"
                )

                LeftBottomSection(
                    modifier = Modifier.align(Alignment.BottomStart),
                    screenInfo = screenInfo,
                )
                RightBottomSection(
                    modifier = Modifier.align(Alignment.BottomEnd),
                    screenInfo = screenInfo,
                )
            }
        }
        DetailScreenTopBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .statusBarsPadding(),
            onBackButtonClicked = onBackButtonClicked,
            onDownloadButtonClicked = {
                convertToBitmap = true
                createdBitmap?.let(onDownloadButtonClicked)
            },
            onShareButtonClicked = {
                convertToBitmap = true
                createdBitmap?.let(onShareButtonClicked)
            }
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(
    enabled: Boolean,
    onDismiss: () -> Unit,
    exportToGalleryClick: () -> Unit,
    shareClick: () -> Unit
) {
    val bottomSheetState = rememberModalBottomSheetState()
    if (enabled) {
        ModalBottomSheet(
            onDismissRequest = { onDismiss() },
            sheetState = bottomSheetState,
            content = {
                BottomSheetContent(
                    exportToGalleryClick = exportToGalleryClick,
                    shareClick = shareClick
                )
            }
        )
    }

}

@Composable
fun BottomSheetContent(
    exportToGalleryClick: () -> Unit,
    shareClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .noRippleClickable { exportToGalleryClick() },
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_download),
                contentDescription = stringResource(id = R.string.premium_icon_content_description),
                tint = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = "Export to gallery",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .noRippleClickable { shareClick() },
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_share),
                contentDescription = stringResource(id = R.string.premium_icon_content_description),
                tint = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = "Share with friends",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}






