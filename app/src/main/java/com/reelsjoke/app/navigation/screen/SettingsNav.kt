package com.reelsjoke.app.navigation.screen

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.reelsjoke.app.core.extensions.openWeb
import com.reelsjoke.app.core.extensions.runIntent
import com.reelsjoke.app.navigation.Screen
import com.reelsjoke.app.presentation.components.BillingBottomSheet
import com.reelsjoke.app.presentation.settings.SettingsScreen
import com.reelsjoke.app.presentation.settings.SettingsScreenUIEffect
import com.reelsjoke.app.presentation.settings.SettingsScreenUIEvent
import com.reelsjoke.app.presentation.settings.SettingsScreenViewModel


/**
 * Created by bedirhansaricayir on 14.02.2024.
 */

fun NavGraphBuilder.settingsScreen(
    popBackStack: () -> Unit
) {
    composable(route = Screen.SettingsScreen.route) {
        val viewModel: SettingsScreenViewModel = hiltViewModel()
        val state = viewModel.state.collectAsStateWithLifecycle()
        val effects = viewModel.effects
        val context = LocalContext.current
        var billingFlowEnabled by remember { mutableStateOf(false) }

        LaunchedEffect(key1 = effects) {
            effects.collect { effect ->
                when (effect) {
                    is SettingsScreenUIEffect.StartBillingFlow -> billingFlowEnabled = true
                    is SettingsScreenUIEffect.NavigateToHomeScreen -> popBackStack()
                    is SettingsScreenUIEffect.RunIntentForShareApp -> context.runIntent(effect.intent)
                    is SettingsScreenUIEffect.RunIntentForSendMail -> context.runIntent(effect.intent)
                    is SettingsScreenUIEffect.ShowPrivacyPolicy -> context.openWeb(effect.url)
                    is SettingsScreenUIEffect.ShowTerms -> context.openWeb(effect.url)
                }
            }
        }

        SettingsScreen(
            state = state.value,
            onEvent = viewModel::onEvent
        )

        BillingBottomSheet(
            enabled = billingFlowEnabled,
            onDismiss = { billingFlowEnabled = false },
            onSuccess = {
                SettingsScreenUIEvent.OnSuccessBilling
                billingFlowEnabled = false
            }
        )

    }
}