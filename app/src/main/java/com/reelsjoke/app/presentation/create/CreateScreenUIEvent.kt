package com.reelsjoke.app.presentation.create

import android.graphics.Bitmap


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
    data class OnIsLikesCountHiddenChanged(val isLikesCountHidden: Boolean) : CreateScreenUIEvent

    data class OnIsTaggedPeopleChanged(val isTaggedPeople: Boolean) : CreateScreenUIEvent

    data class OnPeopleTaggedChanged(val peopleTagged: String) : CreateScreenUIEvent

    data class OnIsLocationExistChanged(val isLocationExist: Boolean) : CreateScreenUIEvent

    data class OnLocationChanged(val location: String) : CreateScreenUIEvent
}
