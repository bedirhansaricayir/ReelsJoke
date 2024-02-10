package com.reelsjoke.app.core.extensions

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.view.Window
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date


/**
 * Created by bedirhansaricayir on 28.01.2024.
 */

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
