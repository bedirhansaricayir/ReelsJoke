package com.reelsjoke.app.core.extensions

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import java.io.ByteArrayOutputStream


/**
 * Created by bedirhansaricayir on 28.01.2024.
 */

fun Bitmap.toBase64(quality: Int = 100): String {
    val baos = ByteArrayOutputStream()
    this.compress(Bitmap.CompressFormat.JPEG, quality, baos)
    val byteArray = baos.toByteArray()
    return Base64.encodeToString(byteArray, Base64.DEFAULT)
}

fun String.toBitmap(): Bitmap? {
    return try {
        val encodeByte = Base64.decode(this, Base64.DEFAULT)
        BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}