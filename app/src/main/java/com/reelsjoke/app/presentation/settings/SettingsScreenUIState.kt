package com.reelsjoke.app.presentation.settings

import com.reelsjoke.app.domain.model.SettingsItemData


/**
 * Created by bedirhansaricayir on 14.02.2024.
 */
data class SettingsScreenUIState(
    val settings: List<SettingsItemData> = emptyList()
)
