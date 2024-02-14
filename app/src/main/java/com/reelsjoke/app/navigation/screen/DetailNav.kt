package com.reelsjoke.app.navigation.screen

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.reelsjoke.app.domain.model.ScreenInfo
import com.reelsjoke.app.navigation.Screen
import com.reelsjoke.app.presentation.detail.DetailScreen


/**
 * Created by bedirhansaricayir on 14.02.2024.
 */

fun NavGraphBuilder.detailScreen(
    screenInfo: ScreenInfo?
) {
    composable(route = Screen.DetailScreen.route) {
        DetailScreen(screenInfo = screenInfo)
    }
}