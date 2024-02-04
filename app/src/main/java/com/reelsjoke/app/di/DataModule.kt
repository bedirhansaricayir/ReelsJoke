package com.reelsjoke.app.di

import android.content.Context
import com.reelsjoke.app.data.cache.AppDatabase
import com.reelsjoke.app.data.cache.ReelsDao
import com.reelsjoke.app.data.datastore.AppPreferences
import com.reelsjoke.app.data.datastore.AppPreferencesImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


/**
 * Created by bedirhansaricayir on 27.01.2024.
 */

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        AppDatabase.buildDatabase(context)


    @Provides
    @Singleton
    fun provideReelsDao(appDatabase: AppDatabase): ReelsDao =
        appDatabase.reelsDao()

    @Provides
    @Singleton
    fun provideAppPreferences(@ApplicationContext context: Context): AppPreferencesImpl =
        AppPreferencesImpl(context)
}