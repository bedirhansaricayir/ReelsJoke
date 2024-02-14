package com.reelsjoke.app.navigation.screen

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.reelsjoke.app.core.extensions.runIntent
import com.reelsjoke.app.navigation.Screen
import com.reelsjoke.app.presentation.settings.SettingsScreen
import com.reelsjoke.app.presentation.settings.SettingsScreenUIEffect
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

        LaunchedEffect(key1 = effects) {
            effects.collect { effect ->
                when (effect) {
                    is SettingsScreenUIEffect.NavigateToHomeScreen -> popBackStack()
                    is SettingsScreenUIEffect.RunIntentForShareApp -> context.runIntent(effect.intent)
                    is SettingsScreenUIEffect.RunIntentForSendMail -> context.runIntent(effect.intent)
                }
            }
        }

        SettingsScreen(
            state = state.value,
            onEvent = viewModel::onEvent
        )
    }
}