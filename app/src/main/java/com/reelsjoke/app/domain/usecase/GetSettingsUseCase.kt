package com.reelsjoke.app.domain.usecase

import com.reelsjoke.app.domain.model.SettingsItemData
import com.reelsjoke.app.domain.repository.SettingsRepository
import javax.inject.Inject


/**
 * Created by bedirhansaricayir on 14.02.2024.
 */
class GetSettingsUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(): Response<List<SettingsItemData>> = settingsRepository.getSettings()
}