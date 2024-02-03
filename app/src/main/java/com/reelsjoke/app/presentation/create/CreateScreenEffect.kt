package com.reelsjoke.app.presentation.create

import com.reelsjoke.app.domain.model.ErrorType


/**
 * Created by bedirhansaricayir on 1.02.2024.
 */

sealed class CreateScreenEffect {
    data class ErrorEffect(val message: String? = null): CreateScreenEffect()
}
