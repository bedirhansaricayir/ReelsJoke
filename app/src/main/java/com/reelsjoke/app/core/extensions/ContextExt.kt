package com.reelsjoke.app.core.extensions

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.net.Uri
import android.view.Window
import androidx.core.content.FileProvider
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Objects


/**
 * Created by bedirhansaricayir on 28.01.2024.
 */


fun Context.runIntent(intent: Intent) {
    when (intent.action) {
        Intent.ACTION_SENDTO, Intent.ACTION_SEND ->
            startActivity(Intent.createChooser(intent, "ReelsJoke"))

        else -> startActivity(intent)
    }
}

fun Context.openWeb(url: String) {
    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
}

fun Context.getUriForFile(): Uri {
    val file = this.createImageFile()
    return FileProvider.getUriForFile(
        Objects.requireNonNull(this),
        this.packageName + ".provider", file
    )
}

fun Context.createImageFile(): File {
    val timestamp = SimpleDateFormat("yyyy_MM_dd_HH:mm:ss").format(Date())
    val imageFileName = "JPEG_" + timestamp + "_"
    val image = File.createTempFile(
        imageFileName,
        ".jpg",
        externalCacheDir
    )
    return image
}

fun Context.findActivity(): Activity? {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    return null
}

tailrec fun Context.findWindow(): Window? =
    when (this) {
        is Activity -> window
        is ContextWrapper -> baseContext.findWindow()
        else -> null
    }

