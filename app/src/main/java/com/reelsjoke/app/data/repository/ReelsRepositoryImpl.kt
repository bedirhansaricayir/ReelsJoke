package com.reelsjoke.app.data.repository

import com.reelsjoke.app.data.cache.ReelsDao
import com.reelsjoke.app.data.mapper.ReelsMapper.cacheToDomain
import com.reelsjoke.app.data.mapper.ReelsMapper.domainToCache
import com.reelsjoke.app.domain.model.ScreenInfo
import com.reelsjoke.app.domain.repository.ReelsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


/**
 * Created by bedirhansaricayir on 27.01.2024.
 */
class ReelsRepositoryImpl @Inject constructor(
    private val dao: ReelsDao
): ReelsRepository {
    override suspend fun getCreatedReels(): Flow<List<ScreenInfo>?> {
        return dao.getCreatedReels().map { list -> list.reversed().map { it.cacheToDomain() } }
    }


    override suspend fun insertReels(item: ScreenInfo) {
        item.domainToCache().let { dao.insertReels(it) }
    }
}