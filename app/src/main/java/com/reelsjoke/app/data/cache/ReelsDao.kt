package com.reelsjoke.app.data.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.reelsjoke.app.data.entity.ScreenInfoEntity
import kotlinx.coroutines.flow.Flow


/**
 * Created by bedirhansaricayir on 27.01.2024.
 */

@Dao
interface ReelsDao {

    @Insert
    suspend fun insertReels(item: ScreenInfoEntity)

    @Query("SELECT * FROM ScreenInfo")
    fun getCreatedReels(): Flow<List<ScreenInfoEntity>>

    @Query("SELECT * FROM ScreenInfo where id=:id")
    fun getReelsById(id: Int): ScreenInfoEntity?
}