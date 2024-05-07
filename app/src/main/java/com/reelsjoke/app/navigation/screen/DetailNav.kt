package com.reelsjoke.app.navigation.screen

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.reelsjoke.app.domain.model.ScreenInfo
import com.reelsjoke.app.navigation.Screen
import com.reelsjoke.app.presentation.detail.DetailScreen
import com.reelsjoke.app.presentation.detail.DetailScreenUIEffect
import com.reelsjoke.app.presentation.detail.DetailScreenViewModel
import com.reelsjoke.app.presentation.detail.util.SaveImageUtility
import com.reelsjoke.app.presentation.detail.util.ShareUtils


/**
 * Created by bedirhansaricayir on 14.02.2024.
 */

@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.detailScreen(
    screenInfo: ScreenInfo?,
    transitionScope: SharedTransitionScope,
    navigateToHome: () -> Unit
) {
    composable(route = Screen.DetailScreen.route) {
        val viewModel: DetailScreenViewModel = hiltViewModel()
        val state = viewModel.state.collectAsStateWithLifecycle()
        val effects = viewModel.effects
        val context = LocalContext.current

        LaunchedEffect(key1 = screenInfo) {
            screenInfo?.let {
                viewModel.stateUpdate(it)
            }
        }

        LaunchedEffect(key1 = effects) {
            effects.collect { effect ->
                when(effect) {
                    is DetailScreenUIEffect.NavigateToHomeScreen -> navigateToHome()
                    is DetailScreenUIEffect.SaveContent -> SaveImageUtility.saveImage(effect.content, context)
                    is DetailScreenUIEffect.RunIntentForShareContent -> ShareUtils.shareImageToOthers(context,bitmap = effect.content)
                }
            }
        }

        transitionScope.DetailScreen(
            state = state.value,
            animatedVisibilityScope = this,
            onEvent = viewModel::onEvent
        )
    }
}