package com.reelsjoke.app.presentation.home

import com.reelsjoke.app.domain.model.ScreenInfo


/**
 * Created by bedirhansaricayir on 3.02.2024.
 */

sealed interface HomeScreenUIEvent {
    data object OnFabClicked : HomeScreenUIEvent
    data class OnItemClicked(val item: ScreenInfo) : HomeScreenUIEvent
    data object OnBalloonShown : HomeScreenUIEvent
    data object OnSettingsClicked : HomeScreenUIEvent
}
