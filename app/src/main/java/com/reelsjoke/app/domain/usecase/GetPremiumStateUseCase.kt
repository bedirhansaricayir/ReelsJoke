package com.reelsjoke.app.domain.usecase

import com.reelsjoke.app.data.datastore.AppPreferencesImpl
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPremiumStateUseCase @Inject constructor(
    private val appPreferencesImpl: AppPreferencesImpl
) {
    operator fun invoke(): Flow<Boolean> {
        return appPreferencesImpl.getPremiumState()
    }
}