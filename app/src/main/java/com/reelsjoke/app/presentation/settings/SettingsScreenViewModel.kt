package com.reelsjoke.app.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reelsjoke.app.core.Contants.Companion.PRIVACY_POLICY
import com.reelsjoke.app.core.Contants.Companion.TERMS
import com.reelsjoke.app.core.extensions.logEffectTriggered
import com.reelsjoke.app.core.extensions.setPremiumUser
import com.reelsjoke.app.domain.repository.AnalyticsHelper
import com.reelsjoke.app.domain.usecase.GetSettingsUseCase
import com.reelsjoke.app.domain.usecase.SetPremiumUseCase
import com.reelsjoke.app.domain.usecase.successOr
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * Created by bedirhansaricayir on 14.02.2024.
 */

@HiltViewModel
class SettingsScreenViewModel @Inject constructor(
    private val getSettingsUseCase: GetSettingsUseCase,
    private val setPremiumUseCase: SetPremiumUseCase,
    private val analyticsHelper: AnalyticsHelper
) : ViewModel() {

    private val _state: MutableStateFlow<SettingsScreenUIState> =
        MutableStateFlow(SettingsScreenUIState())
    val state = _state.asStateFlow()

    private val _effects = Channel<SettingsScreenUIEffect>()
    val effects = _effects.receiveAsFlow()

    init {
        getSettings()
    }

    fun onEvent(event: SettingsScreenUIEvent) {
        when (event) {
            is SettingsScreenUIEvent.OnPremiumClicked -> sendEffect(SettingsScreenUIEffect.StartBillingFlow)
            is SettingsScreenUIEvent.OnBackButtonClicked -> sendEffect(SettingsScreenUIEffect.NavigateToHomeScreen)
            is SettingsScreenUIEvent.OnShareAppClicked -> sendEffect(SettingsScreenUIEffect.RunIntentForShareApp(event.intent))
            is SettingsScreenUIEvent.OnSendFeedbackClicked -> sendEffect(SettingsScreenUIEffect.RunIntentForSendMail(event.intent))
            is SettingsScreenUIEvent.OnPrivacyPolicyClicked -> sendEffect(SettingsScreenUIEffect.ShowPrivacyPolicy(PRIVACY_POLICY))
            is SettingsScreenUIEvent.OnTermsClicked -> sendEffect(SettingsScreenUIEffect.ShowTerms(TERMS))
            is SettingsScreenUIEvent.OnSuccessBilling -> setPremium()
        }
    }

    private fun sendEffect(effect: SettingsScreenUIEffect) {
        analyticsHelper.logEffectTriggered("settings_screen",effect)
        viewModelScope.launch(Dispatchers.IO) {
            _effects.send(effect)
        }
    }

    private fun getSettings() = viewModelScope.launch {
        val settingsDeferred = async { getSettingsUseCase.invoke() }
        val settings = settingsDeferred.await().successOr(emptyList())
        _state.update { uiState ->
            uiState.copy(settings = settings)
        }
    }

    private fun setPremium(state: Boolean = true) {
        analyticsHelper.setPremiumUser()
        viewModelScope.launch(Dispatchers.IO) {
            setPremiumUseCase.invoke(state)
        }
    }
}