package com.reelsjoke.app.navigation.screen

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.reelsjoke.app.domain.model.ScreenInfo
import com.reelsjoke.app.navigation.Screen
import com.reelsjoke.app.presentation.components.BillingBottomSheet
import com.reelsjoke.app.presentation.home.HomeScreen
import com.reelsjoke.app.presentation.home.HomeScreenUIEffect
import com.reelsjoke.app.presentation.home.HomeScreenUIEvent
import com.reelsjoke.app.presentation.home.HomeScreenViewModel


/**
 * Created by bedirhansaricayir on 14.02.2024.
 */

@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.homeScreen(
    transitionScope: SharedTransitionScope,
    navigateToCreate: () -> Unit,
    navigateToDetail: (screenInfo: ScreenInfo) -> Unit,
    navigateToSettings: () -> Unit
) {
    composable(route = Screen.HomeScreen.route) {
        val viewModel: HomeScreenViewModel = hiltViewModel()
        val homeUIState = viewModel.state.collectAsStateWithLifecycle()
        val homeUIEffect = viewModel.effects
        var billingFlowEnabled by remember { mutableStateOf(false) }

        LaunchedEffect(key1 = homeUIEffect) {
            homeUIEffect.collect { effects ->
                when (effects) {
                    is HomeScreenUIEffect.NavigateToCreateScreen -> navigateToCreate()
                    is HomeScreenUIEffect.NavigateToDetailScreen -> navigateToDetail(effects.item)
                    is HomeScreenUIEffect.NavigateToSettingsScreen -> navigateToSettings()
                    is HomeScreenUIEffect.StartBillingFlow -> billingFlowEnabled = true
                }
            }
        }
        transitionScope.HomeScreen(
            homeUIState = homeUIState.value,
            animatedVisibilityScope = this,
            onEvent = viewModel::onEvent,
        )

        BillingBottomSheet(
            enabled = billingFlowEnabled,
            onDismiss = { billingFlowEnabled = false },
            onSuccess = { HomeScreenUIEvent.OnSuccessBilling }
        )

    }
}