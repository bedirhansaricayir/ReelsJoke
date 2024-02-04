package com.reelsjoke.app.domain.model

import android.graphics.Bitmap
import com.reelsjoke.app.core.extensions.toBase64
import java.io.Serializable


/**
 * Created by bedirhansaricayir on 20.01.2024.
 */

data class ScreenInfo(
    var description: String,
    var isLiked: Boolean,
    var likes: String,
    var commentCount: String,
    var send: String,
    var taggedPeople: String? = null,
    var location: String? = null,
    var backgroundImage: Bitmap?,
    var soundImage: Bitmap?,
    var userImage: Bitmap?,
    var userTitle: String,
    var isUserFollowed: Boolean,
) : Serializable {

    fun isValid(): Boolean {
        return backgroundImage?.toBase64()?.isNotEmpty() == true &&
                soundImage?.toBase64()?.isNotEmpty() == true &&
                userImage?.toBase64()?.isNotEmpty() == true &&
                userTitle.isNotEmpty() &&
                commentCount.isNotEmpty() &&
                send.isNotEmpty() &&
                description.isNotEmpty() &&
                commentCount.isNotEmpty() &&
                send.isNotEmpty()
    }
}