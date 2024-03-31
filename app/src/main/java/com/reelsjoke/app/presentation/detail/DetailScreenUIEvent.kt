package com.reelsjoke.app.presentation.detail

import android.graphics.Bitmap

sealed interface DetailScreenUIEvent {
    data object OnBackButtonClicked: DetailScreenUIEvent
    data class OnDownloadButtonClicked(val content: Bitmap): DetailScreenUIEvent
    data class OnShareButtonClicked(val content: Bitmap): DetailScreenUIEvent
}