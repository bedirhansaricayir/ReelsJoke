package com.reelsjoke.app.presentation.settings

import android.content.Intent


/**
 * Created by bedirhansaricayir on 14.02.2024.
 */

sealed interface SettingsScreenUIEffect {

    data object NavigateToHomeScreen : SettingsScreenUIEffect
    data class RunIntentForShareApp(val intent: Intent) : SettingsScreenUIEffect
    data class RunIntentForSendMail(val intent: Intent) : SettingsScreenUIEffect
    data class ShowPrivacyPolicy(val url: String) : SettingsScreenUIEffect
    data class ShowTerms(val url: String) : SettingsScreenUIEffect
}