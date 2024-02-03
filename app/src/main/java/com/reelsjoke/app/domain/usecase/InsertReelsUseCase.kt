package com.reelsjoke.app.domain.usecase

import com.reelsjoke.app.domain.model.ScreenInfo
import com.reelsjoke.app.domain.repository.ReelsRepository
import javax.inject.Inject


/**
 * Created by bedirhansaricayir on 27.01.2024.
 */
class InsertReelsUseCase @Inject constructor(
    private val repository: ReelsRepository
) {
    suspend operator fun invoke(screenInfo: ScreenInfo) {
        repository.insertReels(screenInfo)
    }
}