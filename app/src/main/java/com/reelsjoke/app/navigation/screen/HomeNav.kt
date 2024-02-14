package com.reelsjoke.app.navigation.screen

import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.reelsjoke.app.domain.model.ScreenInfo
import com.reelsjoke.app.navigation.Screen
import com.reelsjoke.app.navigation.canGoNavigate
import com.reelsjoke.app.presentation.home.HomeScreen
import com.reelsjoke.app.presentation.home.HomeScreenUIEffect
import com.reelsjoke.app.presentation.home.HomeScreenViewModel


/**
 * Created by bedirhansaricayir on 14.02.2024.
 */

fun NavGraphBuilder.homeScreen(
    navigateToCreate: () -> Unit,
    navigateToDetail: (screenInfo: ScreenInfo) -> Unit,
    navigateToSettings: () -> Unit
) {
    composable(route = Screen.HomeScreen.route) {
        val viewModel: HomeScreenViewModel = hiltViewModel()
        val homeUIState = viewModel.state.collectAsStateWithLifecycle()
        val homeUIEffect = viewModel.effects
        val balloonState = viewModel.balloonState.collectAsStateWithLifecycle()

        LaunchedEffect(key1 = homeUIEffect) {
            homeUIEffect.collect { effects ->
                when (effects) {
                    is HomeScreenUIEffect.NavigateToCreateScreen -> navigateToCreate()
                    is HomeScreenUIEffect.NavigateToDetailScreen -> navigateToDetail(effects.item)
                    is HomeScreenUIEffect.NavigateToSettingsScreen -> navigateToSettings()
                }
            }
        }
        HomeScreen(
            homeUIState = homeUIState.value,
            balloonState = balloonState.value,
            onEvent = viewModel::onEvent,
        )
    }
}