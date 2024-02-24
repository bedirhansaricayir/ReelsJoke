package com.reelsjoke.app.di

import android.content.Context
import com.reelsjoke.app.billing.BillingClientWrapper
import com.reelsjoke.app.domain.repository.IBillingClient
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by bedirhansaricayir on 22.02.2024.
 */


@Module
@InstallIn(SingletonComponent::class)
object BillingModule {

    @Provides
    @Singleton
    fun provideBillingClientWrapper(
        @ApplicationContext context: Context,
    ): BillingClientWrapper = BillingClientWrapper(context)
}

@Module
@InstallIn(ViewModelComponent::class)
abstract class BillingAbstractModule {

    @Binds
    abstract fun provideIBillingClient(billingClientWrapper: BillingClientWrapper): IBillingClient

}

