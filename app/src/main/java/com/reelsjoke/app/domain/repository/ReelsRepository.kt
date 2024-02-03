package com.reelsjoke.app.domain.repository

import com.reelsjoke.app.domain.model.ScreenInfo
import kotlinx.coroutines.flow.Flow


/**
 * Created by bedirhansaricayir on 27.01.2024.
 */
interface ReelsRepository {

    suspend fun getCreatedReels(): Flow<List<ScreenInfo>?>

    suspend fun insertReels(item: ScreenInfo)
}