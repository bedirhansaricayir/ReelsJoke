package com.reelsjoke.app.core.util

import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.window.DialogWindowProvider
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.reelsjoke.app.core.extensions.findWindow

/**
 * Created by bedirhansaricayir on 28.01.2024.
 */


object IntentUtility {
    private const val message =
        "\nYou should definitely try this app\n\n" + "https://play.google.com/store/apps/details?id=com.reelsjoke.app"

    fun shareAppIntent() = Intent().apply {
        action = Intent.ACTION_SEND
        type = "text/plain"
        putExtra(Intent.EXTRA_TITLE, "ReelsJoke")
        putExtra(Intent.EXTRA_TEXT, message)
    }

    fun sendMailIntent() = Intent().apply {
        action = Intent.ACTION_SENDTO
        data = Uri.parse("mailto:reelsjokeapp@gmail.com")
    }
}

object SystemBarUtility {
    @Composable
    fun SetSystemUIController(hide: Boolean) {
        val window = (LocalView.current.parent as? DialogWindowProvider)?.window
            ?: LocalView.current.context.findWindow()
        if (window != null) {
            WindowCompat.setDecorFitsSystemWindows(window, false)
            val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)

            windowInsetsController.apply {
                systemBarsBehavior = if (hide) {
                    hide(WindowInsetsCompat.Type.statusBars())
                    hide(WindowInsetsCompat.Type.navigationBars())
                    WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                } else {
                    show(WindowInsetsCompat.Type.statusBars())
                    show(WindowInsetsCompat.Type.navigationBars())
                    WindowInsetsControllerCompat.BEHAVIOR_DEFAULT
                }
            }
        }
    }
}
