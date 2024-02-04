package com.reelsjoke.app.presentation.home

import com.reelsjoke.app.domain.model.ScreenInfo


/**
 * Created by bedirhansaricayir on 27.01.2024.
 */

sealed class HomeScreenUIState {
    data object Loading : HomeScreenUIState()
    data class Success(val data: List<ScreenInfo>?) : HomeScreenUIState()
    data class Error(val error: String?) : HomeScreenUIState()
}