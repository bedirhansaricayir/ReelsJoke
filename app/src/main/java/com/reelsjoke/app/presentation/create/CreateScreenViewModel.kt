package com.reelsjoke.app.presentation.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reelsjoke.app.core.extensions.logEffectTriggered
import com.reelsjoke.app.domain.model.BottomSheetType
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
            is CreateScreenUIEvent.OnBackgroundImageChanged -> updateState { it.copy(backgroundImage = event.backgroundImage) }
            is CreateScreenUIEvent.OnIsLikedChanged -> updateState { it.copy(isLiked = event.isLiked) }
            is CreateScreenUIEvent.OnLikesCountChanged -> updateState { it.copy(likesCount = event.likesCount) }
            is CreateScreenUIEvent.OnCommentCountChanged -> updateState { it.copy(commentCount = event.commentCount) }
            is CreateScreenUIEvent.OnSendCountChanged -> updateState { it.copy(sendCount = event.sendCount) }
            is CreateScreenUIEvent.OnUserImageChanged -> updateState { it.copy(userImage = event.userImage) }
            is CreateScreenUIEvent.OnUsernameChanged -> updateState { it.copy(username = event.username) }
            is CreateScreenUIEvent.OnDescriptionChanged -> updateState { it.copy(description = event.description) }
            is CreateScreenUIEvent.OnIsFollowedChanged -> updateState { it.copy(isFollowed = event.isFollowed) }
            is CreateScreenUIEvent.OnPeopleTaggedChanged -> updateState { it.copy(peopleTagged = event.peopleTagged) }
            is CreateScreenUIEvent.OnLocationChanged -> updateState { it.copy(location = event.location) }
            is CreateScreenUIEvent.OnGalleryLauncherStateChanged -> updateState { it.copy(launcherIsOpen = !it.launcherIsOpen) }
            is CreateScreenUIEvent.OnVoiceNameChanged -> updateState { it.copy(voiceName = event.voiceName) }
            is CreateScreenUIEvent.OnIsVerifiedChanged -> updateState { it.copy(isVerified = event.isVerified) }
            is CreateScreenUIEvent.AddLocationClicked -> updateState { it.copy(bottomSheetType = event.bottomSheetType) }
            is CreateScreenUIEvent.ChangeVoiceClicked -> updateState { it.copy(bottomSheetType = event.bottomSheetType) }
            is CreateScreenUIEvent.FeedbackClicked -> updateState { it.copy(bottomSheetType = event.bottomSheetType) }
            is CreateScreenUIEvent.TagPeopleClicked -> updateState { it.copy(bottomSheetType = event.bottomSheetType) }
            is CreateScreenUIEvent.UserInformationClicked -> updateState { it.copy(bottomSheetType = event.bottomSheetType) }
            is CreateScreenUIEvent.BottomSheetDismissClicked -> updateState { it.copy(bottomSheetType = event.bottomSheetType) }
            is CreateScreenUIEvent.BottomSheetDoneClicked -> updateState { it.copy(bottomSheetType = BottomSheetType.NONE) }
            is CreateScreenUIEvent.BottomSheetUsernameClicked -> updateState { it.copy(bottomSheetType = event.bottomSheetType) }

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
                likesCount,
                commentCount,
                sendCount,
                peopleTagged,
                location,
                backgroundImage,
                userImage,
                userImage,
                username,
                isFollowed,
                isVerified,
                voiceName
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
    private fun updateState(update: (CreateScreenUIState) -> CreateScreenUIState) = _state.update(update)



}