package com.reelsjoke.app.domain.usecase

import com.reelsjoke.app.data.datastore.AppPreferencesImpl
import javax.inject.Inject


/**
 * Created by bedirhansaricayir on 3.02.2024.
 */

class SetBalloonStateUseCase @Inject constructor(
    private val appPreferences: AppPreferencesImpl
) {
    suspend operator fun invoke(state: Boolean) {
        appPreferences.setBalloonState(state)
    }
}