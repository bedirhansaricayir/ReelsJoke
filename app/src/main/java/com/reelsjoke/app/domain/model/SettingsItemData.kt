package com.reelsjoke.app.domain.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Stable


/**
 * Created by bedirhansaricayir on 14.02.2024.
 */

@Stable
data class SettingsItemData(
    @DrawableRes val leadingIcon: Int,
    @StringRes val leadingIconContentDescription: Int,
    @StringRes val title: Int
)
