package com.reelsjoke.app.data.cache

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.reelsjoke.app.data.entity.ScreenInfoEntity


/**
 * Created by bedirhansaricayir on 27.01.2024.
 */

@Database(entities = [ScreenInfoEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun reelsDao(): ReelsDao

    companion object {
        private const val databaseName = "screenInfoDb"

        fun buildDatabase(context: Context): AppDatabase =
            Room.databaseBuilder(context, AppDatabase::class.java, databaseName)
                .fallbackToDestructiveMigration()
                .build()
    }
}