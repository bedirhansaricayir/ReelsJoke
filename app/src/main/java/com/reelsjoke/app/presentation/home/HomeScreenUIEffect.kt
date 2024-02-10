package com.reelsjoke.app.presentation.home

import com.reelsjoke.app.domain.model.ScreenInfo


/**
 * Created by bedirhansaricayir on 3.02.2024.
 */

sealed interface HomeScreenUIEffect {
    data object NavigateToCreateScreen : HomeScreenUIEffect
    data class NavigateToDetailScreen(val item: ScreenInfo) : HomeScreenUIEffect
    data object NavigateToSettingsScreen : HomeScreenUIEffect
}
