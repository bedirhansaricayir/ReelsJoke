package com.reelsjoke.app.domain.repository

import com.reelsjoke.app.domain.model.SettingsItemData
import com.reelsjoke.app.domain.usecase.Response


/**
 * Created by bedirhansaricayir on 14.02.2024.
 */
interface SettingsRepository {

    suspend fun getSettings(): Response<List<SettingsItemData>>
}