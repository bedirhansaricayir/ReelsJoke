package com.reelsjoke.app.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reelsjoke.app.domain.usecase.GetBalloonStateUseCase
import com.reelsjoke.app.domain.usecase.GetCreatedReelsUseCase
import com.reelsjoke.app.domain.usecase.GetPremiumStateUseCase
import com.reelsjoke.app.domain.usecase.Response
import com.reelsjoke.app.domain.usecase.SetBalloonStateUseCase
import com.reelsjoke.app.domain.usecase.SetPremiumUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
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
    private val setBalloonStateUseCase: SetBalloonStateUseCase,
    private val setPremiumUseCase: SetPremiumUseCase,
    private val getPremiumStateUseCase: GetPremiumStateUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<HomeScreenUIState> =
        MutableStateFlow(HomeScreenUIState.Loading)
    val state = _state.asStateFlow()

    private val _effects = Channel<HomeScreenUIEffect>()
    val effects = _effects.receiveAsFlow()


    init {
        getStateFlow()
    }

    fun onEvent(event: HomeScreenUIEvent) {
        when (event) {
            is HomeScreenUIEvent.OnFabClicked -> sendEffect(HomeScreenUIEffect.NavigateToCreateScreen)
            is HomeScreenUIEvent.OnItemClicked -> sendEffect(HomeScreenUIEffect.NavigateToDetailScreen(event.item))
            is HomeScreenUIEvent.OnBalloonShown -> setBalloonState()
            is HomeScreenUIEvent.OnSettingsClicked -> sendEffect(HomeScreenUIEffect.NavigateToSettingsScreen)
            is HomeScreenUIEvent.OnReachTheLimit -> sendEffect(HomeScreenUIEffect.StartBillingFlow)
            is HomeScreenUIEvent.OnSuccessBilling -> setPremium()
            is HomeScreenUIEvent.OnRefreshButtonClicked -> getStateFlow()
        }
    }

    private fun sendEffect(effect: HomeScreenUIEffect) {
        viewModelScope.launch(Dispatchers.IO) {
            _effects.send(effect)
        }
    }

    private fun getStateFlow() {
        viewModelScope.launch(Dispatchers.IO) {
            val createdReelsFlow = getCreatedReelsUseCase.invoke()
            val premiumStateFlow = getPremiumStateUseCase.invoke()
            val balloonStateFlow = getBalloonStateUseCase.invoke()
            combine(
                createdReelsFlow,
                premiumStateFlow,
                balloonStateFlow
            ) { createdReelsResponse, isPremium, isBalloonShown ->
                when (createdReelsResponse) {
                    is Response.Loading -> HomeScreenUIState.Loading
                    is Response.Success -> HomeScreenUIState.Success(
                        createdReelsResponse.data,
                        isPremium,
                        isBalloonShown
                    )

                    is Response.Error -> HomeScreenUIState.Error(createdReelsResponse.errorMessage)
                }
            }.collect { newState ->
                _state.value = newState
            }
        }
    }

    private fun setBalloonState(state: Boolean = true) {
        viewModelScope.launch(Dispatchers.IO) {
            setBalloonStateUseCase.invoke(state)
        }
    }

    private fun setPremium(state: Boolean = true) {
        viewModelScope.launch(Dispatchers.IO) {
            setPremiumUseCase.invoke(state)
        }
    }
}