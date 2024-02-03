package com.reelsjoke.app.presentation.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reelsjoke.app.core.extensions.toBase64
import com.reelsjoke.app.domain.model.ErrorType
import com.reelsjoke.app.domain.model.ScreenInfo
import com.reelsjoke.app.domain.usecase.InsertReelsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * Created by bedirhansaricayir on 28.01.2024.
 */

@HiltViewModel
class CreateScreenViewModel @Inject constructor(
    private val insertReelsUseCase: InsertReelsUseCase
): ViewModel() {

    private val _state = MutableStateFlow(CreateScreenUIState(isLoading = false))
    val state: StateFlow<CreateScreenUIState> = _state.asStateFlow()

    private val _effects = Channel<CreateScreenEffect>()
    val effect  = _effects.receiveAsFlow()

    fun onEvent(event: CreateScreenEvent) {
        when (event) {
            is CreateScreenEvent.OnExportClicked -> exportClicked(event.screenInfo)
        }
    }

    private fun exportClicked(screenInfo: ScreenInfo) {
        val isValidModel = checkScreenInfoModel(screenInfo = screenInfo)
        if (isValidModel) insertReels(screenInfo) else sendError(screenInfo)
    }

    private fun sendError(screenInfo: ScreenInfo) = viewModelScope.launch {
        val message = handleScreenInfoError(screenInfo)
        _effects.send(CreateScreenEffect.ErrorEffect(message))
    }

    private fun handleScreenInfoError(screenInfo: ScreenInfo): String? {
        screenInfo.apply {
            if (backgroundImage?.toBase64()?.isEmpty() == true)
                return ErrorType.BACKGROUND_IMAGE.message
            if (commentCount.isEmpty())
                return ErrorType.COMMENT_COUNT.message
            if (send.isEmpty())
                return ErrorType.SEND_COUNT.message
            if (userImage?.toBase64()?.isEmpty() == true)
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

    private fun checkScreenInfoModel(screenInfo: ScreenInfo): Boolean = screenInfo.isValid()


}