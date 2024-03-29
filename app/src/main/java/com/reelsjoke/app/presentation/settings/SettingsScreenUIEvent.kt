package com.reelsjoke.app.presentation.settings

import android.content.Intent


/**
 * Created by bedirhansaricayir on 14.02.2024.
 */
sealed interface SettingsScreenUIEvent {

    data object OnPremiumClicked : SettingsScreenUIEvent
    data object OnBackButtonClicked : SettingsScreenUIEvent
    data class OnShareAppClicked(val intent: Intent) : SettingsScreenUIEvent
    data class OnSendFeedbackClicked(val intent: Intent) : SettingsScreenUIEvent
    data object OnPrivacyPolicyClicked : SettingsScreenUIEvent
    data object OnTermsClicked : SettingsScreenUIEvent
    data object OnSuccessBilling : SettingsScreenUIEvent

}
