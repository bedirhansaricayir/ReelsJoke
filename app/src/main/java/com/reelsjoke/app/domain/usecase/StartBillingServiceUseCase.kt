package com.reelsjoke.app.domain.usecase

import com.reelsjoke.app.domain.repository.IBillingClient
import javax.inject.Inject


/**
 * Created by bedirhansaricayir on 22.02.2024.
 */
class StartBillingServiceUseCase @Inject constructor(
    private val iBillingClient: IBillingClient
) {
    operator fun invoke() = iBillingClient.initBilling()
}