package com.reelsjoke.app.presentation.create

import com.reelsjoke.app.domain.model.ScreenInfo


/**
 * Created by bedirhansaricayir on 28.01.2024.
 */

sealed class CreateScreenEvent {
    data class OnExportClicked(val screenInfo: ScreenInfo): CreateScreenEvent()
}
