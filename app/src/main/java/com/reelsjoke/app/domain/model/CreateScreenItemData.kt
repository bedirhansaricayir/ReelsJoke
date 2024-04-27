package com.reelsjoke.app.domain.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Stable


/**
 * Created by bedirhansaricayir on 26.04.2024.
 */

@Stable
data class CreateScreenItemData(
    @DrawableRes val leadingIcon: Int,
    @StringRes val title: Int
)
