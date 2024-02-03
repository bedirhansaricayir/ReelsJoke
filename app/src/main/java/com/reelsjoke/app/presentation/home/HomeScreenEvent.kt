package com.reelsjoke.app.presentation.home

import com.reelsjoke.app.domain.model.ScreenInfo


/**
 * Created by bedirhansaricayir on 3.02.2024.
 */

sealed class HomeScreenEvent {
    data object OnFabClicked: HomeScreenEvent()
    data class OnItemClicked(val item: ScreenInfo): HomeScreenEvent()
}
