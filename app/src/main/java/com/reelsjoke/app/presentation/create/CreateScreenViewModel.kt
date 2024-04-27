package com.reelsjoke.app.presentation.create

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reelsjoke.app.core.extensions.logEffectTriggered
import com.reelsjoke.app.domain.model.ErrorType
import com.reelsjoke.app.domain.model.ScreenInfo
import com.reelsjoke.app.domain.repository.AnalyticsHelper
import com.reelsjoke.app.domain.usecase.GetCreateItemsUseCase
import com.reelsjoke.app.domain.usecase.InsertReelsUseCase
import com.reelsjoke.app.domain.usecase.successOr
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * Created by bedirhansaricayir on 28.01.2024.
 */

@HiltViewModel
class CreateScreenViewModel @Inject constructor(
    private val insertReelsUseCase: InsertReelsUseCase,
    private val analyticsHelper: AnalyticsHelper,
    private val getCreateItemsUseCase: GetCreateItemsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(CreateScreenUIState(isLoading = false))
    val state: StateFlow<CreateScreenUIState> = _state.asStateFlow()

    private val _effects = Channel<CreateScreenUIEffect>()
    val effect = _effects.receiveAsFlow()

    init {
        getItems()
    }

    fun onEvent(event: CreateScreenUIEvent) {
        when (event) {
            is CreateScreenUIEvent.OnExportClicked -> exportClicked()
            is CreateScreenUIEvent.OnBackButtonClicked -> sendEffect(CreateScreenUIEffect.NavigateToHomeScreen)
            is CreateScreenUIEvent.OnBackgroundImageChanged -> setBackgroundImage(event.backgroundImage)
            is CreateScreenUIEvent.OnIsLikedChanged -> setIsLiked(event.isLiked)
            is CreateScreenUIEvent.OnLikesCountChanged -> setLikesCount(event.likesCount)
            is CreateScreenUIEvent.OnCommentCountChanged -> setCommentCount(event.commentCount)
            is CreateScreenUIEvent.OnSendCountChanged -> setSendCount(event.sendCount)
            is CreateScreenUIEvent.OnUserImageChanged -> setUserImage(event.userImage)
            is CreateScreenUIEvent.OnUsernameChanged -> setUsername(event.username)
            is CreateScreenUIEvent.OnDescriptionChanged -> setDescription(event.description)
            is CreateScreenUIEvent.OnIsFollowedChanged -> setIsFollowed(event.isFollowed)
            is CreateScreenUIEvent.OnIsLikesCountHiddenChanged -> setIsLikesCountHidden(event.isLikesCountHidden)
            is CreateScreenUIEvent.OnIsTaggedPeopleChanged -> setIsTaggedPeople(event.isTaggedPeople)
            is CreateScreenUIEvent.OnPeopleTaggedChanged -> setPeopleTagged(event.peopleTagged)
            is CreateScreenUIEvent.OnIsLocationExistChanged -> setIsLocationExist(event.isLocationExist)
            is CreateScreenUIEvent.OnLocationChanged -> setLocation(event.location)
        }
    }

    private fun getItems() = viewModelScope.launch {
        val itemsDeferred = async { getCreateItemsUseCase.invoke() }
        val items = itemsDeferred.await().successOr(emptyList())
        _state.update { uiState ->
            uiState.copy(items = items)
        }
    }

    private fun sendEffect(effect: CreateScreenUIEffect) {
        analyticsHelper.logEffectTriggered("create_screen",effect)
        viewModelScope.launch(Dispatchers.IO) {
            _effects.send(effect)
        }
    }


    private fun exportClicked() {
        viewModelScope.launch {
            val screenInfo = createSnapshotModel()
            if (screenInfo.isValid()) {
                fakeDelayToAnimation()
                insertReels(screenInfo)
                sendEffect(CreateScreenUIEffect.NavigateToDetailScreen(screenInfo))
            } else sendEffect(CreateScreenUIEffect.ErrorEffect(handleScreenInfoError(screenInfo)))
        }

    }

    private suspend fun fakeDelayToAnimation(timeMillis: Long = 1000) {
        _state.update { state ->
            state.copy(infiniteRepeatable = true)
        }
        delay(timeMillis)
        _state.update { state ->
            state.copy(infiniteRepeatable = false)
        }
    }

    private fun createSnapshotModel(): ScreenInfo {
        _state.value.apply {
            return ScreenInfo(
                description,
                isLiked,
                if (isLikesCountHidden) "Likes" else likesCount,
                commentCount,
                sendCount,
                peopleTagged,
                location,
                backgroundImage,
                userImage,
                userImage,
                username,
                isFollowed
            )
        }
    }

    private fun handleScreenInfoError(screenInfo: ScreenInfo): String? {
        screenInfo.apply {
            if (backgroundImage == null)
                return ErrorType.BACKGROUND_IMAGE.message
            if (commentCount.isEmpty())
                return ErrorType.COMMENT_COUNT.message
            if (send.isEmpty())
                return ErrorType.SEND_COUNT.message
            if (userImage == null)
                return ErrorType.USER_IMAGE.message
            if (userTitle.isEmpty())
                return ErrorType.USER_TITLE.message
            if (description.isEmpty())
                return ErrorType.DESCRIPTION.message
            else
                return null
        }
    }


    private fun insertReels(screenInfo: ScreenInfo) {
        viewModelScope.launch(Dispatchers.IO) {
            insertReelsUseCase.invoke(screenInfo)
        }
    }

    private fun setBackgroundImage(backgroundImage: Bitmap) {
        _state.update { state ->
            state.copy(backgroundImage = backgroundImage)
        }
    }

    private fun setIsLiked(isLiked: Boolean) {
        _state.update { state ->
            state.copy(isLiked = isLiked)
        }
    }

    private fun setLikesCount(likesCount: String) {
        _state.update { state ->
            state.copy(likesCount = likesCount)
        }
    }

    private fun setCommentCount(commentCount: String) {
        _state.update { state ->
            state.copy(commentCount = commentCount)
        }
    }

    private fun setSendCount(sendCount: String) {
        _state.update { state ->
            state.copy(sendCount = sendCount)
        }
    }

    private fun setUserImage(userImage: Bitmap) {
        _state.update { state ->
            state.copy(userImage = userImage)
        }
    }

    private fun setUsername(username: String) {
        _state.update { state ->
            state.copy(username = username)
        }
    }

    private fun setDescription(description: String) {
        _state.update { state ->
            state.copy(description = description)
        }
    }

    private fun setIsFollowed(isFollowed: Boolean) {
        _state.update { state ->
            state.copy(isFollowed = isFollowed)
        }
    }

    private fun setIsLikesCountHidden(isLikesCountHidden: Boolean) {
        _state.update { state ->
            state.copy(isLikesCountHidden = isLikesCountHidden)
        }
    }

    private fun setIsTaggedPeople(isTaggedPeople: Boolean) {
        _state.update { state ->
            state.copy(isTaggedPeople = isTaggedPeople)
        }
    }

    private fun setPeopleTagged(peopleTagged: String) {
        _state.update { state ->
            state.copy(peopleTagged = peopleTagged)
        }
    }

    private fun setIsLocationExist(isLocationExist: Boolean) {
        _state.update { state ->
            state.copy(isLocationExist = isLocationExist)
        }
    }

    private fun setLocation(location: String) {
        _state.update { state ->
            state.copy(location = location)
        }
    }
}