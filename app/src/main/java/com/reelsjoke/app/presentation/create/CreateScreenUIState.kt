package com.reelsjoke.app.presentation.create

import android.graphics.Bitmap


/**
 * Created by bedirhansaricayir on 28.01.2024.
 */
data class CreateScreenUIState(
    val isLoading: Boolean = false,
    val infiniteRepeatable: Boolean = false,
    val backgroundImage: Bitmap? = null,
    val isLiked: Boolean = false,
    val likesCount: String = "",
    val commentCount: String = "",
    val sendCount: String = "",
    val userImage: Bitmap? = null,
    val username: String = "",
    val description: String = "",
    val isFollowed: Boolean = false,
    val isLikesCountHidden: Boolean = true,
    val isTaggedPeople: Boolean = false,
    val peopleTagged: String = "",
    val isLocationExist: Boolean = false,
    val location: String = ""
)
