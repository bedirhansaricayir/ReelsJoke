package com.reelsjoke.app.domain.usecase

import com.reelsjoke.app.data.datastore.AppPreferencesImpl
import javax.inject.Inject

class SetPremiumUseCase @Inject constructor(
    private val appPreferences: AppPreferencesImpl
) {
    suspend operator fun invoke(state: Boolean) {
        appPreferences.setPremium(state)
    }
}