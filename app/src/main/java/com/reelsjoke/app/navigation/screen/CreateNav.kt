package com.reelsjoke.app.navigation.screen

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.reelsjoke.app.domain.model.ScreenInfo
import com.reelsjoke.app.navigation.Screen
import com.reelsjoke.app.presentation.create.CreateScreen
import com.reelsjoke.app.presentation.create.CreateScreenUIEffect
import com.reelsjoke.app.presentation.create.CreateScreenViewModel
import kotlinx.coroutines.launch


/**
 * Created by bedirhansaricayir on 14.02.2024.
 */

fun NavGraphBuilder.createScreen(
    navigateToDetail: (screenInfo: ScreenInfo) -> Unit,
    navigateToHome: () -> Unit
) {
    composable(route = Screen.CreateScreen.route) {
        val viewModel: CreateScreenViewModel = hiltViewModel()
        val createUIState = viewModel.state.collectAsStateWithLifecycle()
        val createUIEffect = viewModel.effect
        val snackbarHostState = remember { SnackbarHostState() }
        val scope = rememberCoroutineScope()


        LaunchedEffect(key1 = createUIEffect) {
            createUIEffect.collect { effects ->
                when (effects) {
                    is CreateScreenUIEffect.ErrorEffect -> {
                        effects.message?.let {
                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    message = effects.message,
                                    actionLabel = "OK"
                                )
                            }
                        }
                    }

                    is CreateScreenUIEffect.NavigateToDetailScreen -> navigateToDetail(effects.screenInfo)
                    is CreateScreenUIEffect.NavigateToHomeScreen -> navigateToHome()
                }
            }
        }

        CreateScreen(
            state = createUIState.value,
            snackbarHostState = snackbarHostState,
            onEvent = viewModel::onEvent,
        )
    }
}