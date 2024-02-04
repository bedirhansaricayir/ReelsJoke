package com.reelsjoke.app.presentation.create

import com.reelsjoke.app.domain.model.ScreenInfo


/**
 * Created by bedirhansaricayir on 1.02.2024.
 */

sealed interface CreateScreenUIEffect {
    data class ErrorEffect(val message: String? = null) : CreateScreenUIEffect
    data class NavigateToDetailScreen(val screenInfo: ScreenInfo) : CreateScreenUIEffect
    data object NavigateToHomeScreen : CreateScreenUIEffect
}
