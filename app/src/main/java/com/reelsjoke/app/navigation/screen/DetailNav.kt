package com.reelsjoke.app.navigation.screen

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.reelsjoke.app.core.util.SaveImageUtility
import com.reelsjoke.app.core.util.ShareUtils
import com.reelsjoke.app.domain.model.ScreenInfo
import com.reelsjoke.app.navigation.Screen
import com.reelsjoke.app.presentation.detail.DetailScreen
import com.reelsjoke.app.presentation.detail.DetailScreenUIEffect
import com.reelsjoke.app.presentation.detail.DetailScreenViewModel


/**
 * Created by bedirhansaricayir on 14.02.2024.
 */

fun NavGraphBuilder.detailScreen(
    screenInfo: ScreenInfo?,
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

        DetailScreen(
            state = state.value,
            onEvent = viewModel::onEvent
        )
    }
}