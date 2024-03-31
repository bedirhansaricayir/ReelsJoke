package com.reelsjoke.app.presentation.detail

import android.graphics.Bitmap


sealed interface DetailScreenUIEffect {
    data object NavigateToHomeScreen : DetailScreenUIEffect
    data class RunIntentForShareContent(val content: Bitmap) : DetailScreenUIEffect
    data class SaveContent(val content: Bitmap): DetailScreenUIEffect
}