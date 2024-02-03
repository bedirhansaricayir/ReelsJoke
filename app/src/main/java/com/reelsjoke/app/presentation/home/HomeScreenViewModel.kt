package com.reelsjoke.app.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reelsjoke.app.domain.usecase.GetBalloonStateUseCase
import com.reelsjoke.app.domain.usecase.GetCreatedReelsUseCase
import com.reelsjoke.app.domain.usecase.Response
import com.reelsjoke.app.domain.usecase.SetBalloonStateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by bedirhansaricayir on 27.01.2024.
 */

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val getCreatedReelsUseCase: GetCreatedReelsUseCase,
    private val getBalloonStateUseCase: GetBalloonStateUseCase,
    private val setBalloonStateUseCase: SetBalloonStateUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<HomeScreenUIState> =
        MutableStateFlow(HomeScreenUIState.Loading)
    val state = _state.asStateFlow()

    private val _effects = Channel<HomeScreenUIEffect>()
    val effects = _effects.receiveAsFlow()

    private var balloonState: MutableStateFlow<Boolean> = MutableStateFlow(false)


    init {
        getBalloonState()
        getCreatedReels()
    }

    fun onEvent(event: HomeScreenEvent) {
        when (event) {
            is HomeScreenEvent.OnFabClicked -> sendEffect(HomeScreenUIEffect.NavigateToCreateScreen)
            is HomeScreenEvent.OnItemClicked -> sendEffect(HomeScreenUIEffect.NavigateToDetailScreen(event.item))
        }
    }

    private fun sendEffect(effect: HomeScreenUIEffect) {
        viewModelScope.launch(Dispatchers.IO) {
            _effects.send(effect)
        }
    }

    private fun getCreatedReels() {
        viewModelScope.launch(Dispatchers.IO) {
            getCreatedReelsUseCase.invoke().collect { response ->
                when (response) {
                    is Response.Loading -> _state.value = HomeScreenUIState.Loading
                    is Response.Success -> _state.value =
                        HomeScreenUIState.Success(response.data, showBalloon = balloonState.value)

                    is Response.Error -> _state.value =
                        HomeScreenUIState.Error(response.errorMessage)
                }
            }
        }
    }

    private fun setBalloonState(state: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            setBalloonStateUseCase.invoke(state)
        }
    }

    private fun getBalloonState() {
        viewModelScope.launch(Dispatchers.IO) {
            getBalloonStateUseCase.invoke().collect { state ->
                balloonState.value = state
            }
        }
    }
}