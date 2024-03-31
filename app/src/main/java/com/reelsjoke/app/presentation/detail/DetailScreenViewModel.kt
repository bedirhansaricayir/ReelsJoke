package com.reelsjoke.app.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reelsjoke.app.core.extensions.logEffectTriggered
import com.reelsjoke.app.domain.model.ScreenInfo
import com.reelsjoke.app.domain.repository.AnalyticsHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailScreenViewModel @Inject constructor(
    private val analyticsHelper: AnalyticsHelper
) : ViewModel() {

    private val _state: MutableStateFlow<DetailScreenUIState> =
        MutableStateFlow(DetailScreenUIState())
    val state = _state.asStateFlow()

    private val _effects = Channel<DetailScreenUIEffect>()
    val effects = _effects.receiveAsFlow()

    fun onEvent(event: DetailScreenUIEvent) {
        when(event) {
            DetailScreenUIEvent.OnBackButtonClicked -> sendEffect(DetailScreenUIEffect.NavigateToHomeScreen)
            is DetailScreenUIEvent.OnDownloadButtonClicked -> sendEffect(DetailScreenUIEffect.SaveContent(event.content))
            is DetailScreenUIEvent.OnShareButtonClicked -> sendEffect(DetailScreenUIEffect.RunIntentForShareContent(event.content))
        }
    }
    private fun sendEffect(effect: DetailScreenUIEffect) {
        analyticsHelper.logEffectTriggered("detail_screen",effect)
        viewModelScope.launch(Dispatchers.IO) {
            _effects.send(effect)
        }
    }

    fun stateUpdate(screenInfo: ScreenInfo) {
        _state.update {
            it.copy(screenInfo = screenInfo)
        }
    }
}