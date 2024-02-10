package com.reelsjoke.app.core.util

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.window.DialogWindowProvider
import androidx.core.content.FileProvider
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.reelsjoke.app.core.extensions.createImageFile
import com.reelsjoke.app.core.extensions.findActivity
import com.reelsjoke.app.core.extensions.findWindow
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.util.Objects


/**
 * Created by bedirhansaricayir on 28.01.2024.
 */

object UriUtility {
    fun getUriForFile(context: Context): Uri {
        val file = context.createImageFile()
        return FileProvider.getUriForFile(
            Objects.requireNonNull(context),
            context.packageName + ".provider", file
        )
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

    @Composable
    fun HideSystemBars() {
        val context = LocalContext.current
        DisposableEffect(Unit) {
            val window = context.findActivity()?.window ?: return@DisposableEffect onDispose {}
            val insetsController = WindowCompat.getInsetsController(window, window.decorView)

            insetsController.apply {
                hide(WindowInsetsCompat.Type.statusBars())
                hide(WindowInsetsCompat.Type.navigationBars())
                systemBarsBehavior =
                    WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }

            onDispose {
                insetsController.apply {
                    show(WindowInsetsCompat.Type.statusBars())
                    show(WindowInsetsCompat.Type.navigationBars())
                    systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_DEFAULT
                }
            }
        }
    }
}

object SaveImageUtility {
    fun saveImage(bitmap: Bitmap, context: Context, folderName: String) {
        if (android.os.Build.VERSION.SDK_INT >= 29) {
            val values = contentValues()
            values.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/" + folderName)
            values.put(MediaStore.Images.Media.IS_PENDING, true)
            // RELATIVE_PATH and IS_PENDING are introduced in API 29.

            val uri: Uri? =
                context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
            if (uri != null) {
                saveImageToStream(bitmap, context.contentResolver.openOutputStream(uri))
                values.put(MediaStore.Images.Media.IS_PENDING, false)
                context.contentResolver.update(uri, values, null, null)
            }
        } else {
            val directory = File(
                Environment.getExternalStorageDirectory().toString() + File.separator + folderName
            )
            // getExternalStorageDirectory is deprecated in API 29

            if (!directory.exists()) {
                directory.mkdirs()
            }
            val fileName = System.currentTimeMillis().toString() + ".png"
            val file = File(directory, fileName)
            saveImageToStream(bitmap, FileOutputStream(file))
            if (file.absolutePath != null) {
                val values = contentValues()
                values.put(MediaStore.Images.Media.DATA, file.absolutePath)
                // .DATA is deprecated in API 29
                context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
            }
        }
    }

    private fun contentValues(): ContentValues {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/png")
        values.put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis() / 1000);
        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
        return values
    }

    private fun saveImageToStream(bitmap: Bitmap, outputStream: OutputStream?) {
        if (outputStream != null) {
            try {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                outputStream.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}