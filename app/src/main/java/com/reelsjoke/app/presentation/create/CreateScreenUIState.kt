package com.reelsjoke.app.presentation.create

import com.reelsjoke.app.domain.model.ScreenInfo


/**
 * Created by bedirhansaricayir on 28.01.2024.
 */
data class CreateScreenUIState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val screenInfo: ScreenInfo? = null
)
