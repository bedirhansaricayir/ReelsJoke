package com.reelsjoke.app.di

import com.reelsjoke.app.data.repository.ReelsRepositoryImpl
import com.reelsjoke.app.domain.repository.ReelsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


/**
 * Created by bedirhansaricayir on 27.01.2024.
 */

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideReelsRepository(reelsRepositoryImpl: ReelsRepositoryImpl): ReelsRepository
}