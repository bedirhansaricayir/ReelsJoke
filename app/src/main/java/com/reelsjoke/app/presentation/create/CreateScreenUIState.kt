package com.reelsjoke.app.presentation.create

import android.graphics.Bitmap
import com.reelsjoke.app.domain.model.BottomSheetType
import com.reelsjoke.app.domain.model.CreateScreenItemData


/**
 * Created by bedirhansaricayir on 28.01.2024.
 */
data class CreateScreenUIState(
    val items: List<CreateScreenItemData> = emptyList(),
    val bottomSheetType: BottomSheetType = BottomSheetType.NONE,
    val launcherIsOpen: Boolean = false,
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
    val peopleTagged: String = "",
    val location: String = "",
    var isVerified: Boolean = false,
    var voiceName: String = ""
)
