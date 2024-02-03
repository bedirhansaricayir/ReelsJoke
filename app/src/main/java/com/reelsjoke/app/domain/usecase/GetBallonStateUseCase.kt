package com.reelsjoke.app.domain.usecase

import com.reelsjoke.app.data.datastore.AppPreferencesImpl
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


/**
 * Created by bedirhansaricayir on 3.02.2024.
 */

class GetBalloonStateUseCase @Inject constructor(
    private val appPreferencesImpl: AppPreferencesImpl
) {
    operator fun invoke(): Flow<Boolean> {
        return appPreferencesImpl.getBalloonState()
    }
}