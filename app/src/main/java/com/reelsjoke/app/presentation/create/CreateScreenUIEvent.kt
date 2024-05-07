package com.reelsjoke.app.presentation.create

import android.graphics.Bitmap
import com.reelsjoke.app.domain.model.BottomSheetType


/**
 * Created by bedirhansaricayir on 28.01.2024.
 */

sealed interface CreateScreenUIEvent {
    data object OnExportClicked : CreateScreenUIEvent
    data object OnBackButtonClicked : CreateScreenUIEvent

    data class OnBackgroundImageChanged(val backgroundImage: Bitmap) : CreateScreenUIEvent

    data class OnIsLikedChanged(val isLiked: Boolean) : CreateScreenUIEvent

    data class OnLikesCountChanged(val likesCount: String) : CreateScreenUIEvent
    data class OnCommentCountChanged(val commentCount: String) : CreateScreenUIEvent
    data class OnSendCountChanged(val sendCount: String) : CreateScreenUIEvent

    data class OnUserImageChanged(val userImage: Bitmap) : CreateScreenUIEvent

    data class OnUsernameChanged(val username: String) : CreateScreenUIEvent
    data class OnDescriptionChanged(val description: String) : CreateScreenUIEvent

    data class OnIsFollowedChanged(val isFollowed: Boolean) : CreateScreenUIEvent

    data class OnPeopleTaggedChanged(val peopleTagged: String) : CreateScreenUIEvent

    data class OnLocationChanged(val location: String) : CreateScreenUIEvent

    data class OnIsVerifiedChanged(val isVerified: Boolean) : CreateScreenUIEvent

    data class OnVoiceNameChanged(val voiceName: String) : CreateScreenUIEvent

    data class FeedbackClicked(val bottomSheetType: BottomSheetType) : CreateScreenUIEvent

    data class TagPeopleClicked(val bottomSheetType: BottomSheetType) : CreateScreenUIEvent

    data class AddLocationClicked(val bottomSheetType: BottomSheetType) : CreateScreenUIEvent

    data class ChangeVoiceClicked(val bottomSheetType: BottomSheetType) : CreateScreenUIEvent

    data class UserInformationClicked(val bottomSheetType: BottomSheetType) : CreateScreenUIEvent

    data class BottomSheetDismissClicked(val bottomSheetType: BottomSheetType = BottomSheetType.NONE) : CreateScreenUIEvent

    data object BottomSheetDoneClicked : CreateScreenUIEvent

    data class BottomSheetUsernameClicked(val bottomSheetType: BottomSheetType) : CreateScreenUIEvent

    data object OnGalleryLauncherStateChanged : CreateScreenUIEvent

}
