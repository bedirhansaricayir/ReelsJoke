package com.reelsjoke.app.domain.repository

import android.content.Context
import com.android.billingclient.api.BillingFlowParams
import com.android.billingclient.api.ProductDetails
import com.android.billingclient.api.Purchase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow


/**
 * Created by bedirhansaricayir on 22.02.2024.
 */
interface IBillingClient {
    fun initBilling()
    fun status(): StateFlow<String>
    suspend fun queryProducts()
    fun makePurchase(productDetailsList: List<ProductDetails>)
    fun launchBillingFlow(context: Context, billingFlowParams: BillingFlowParams)
    fun completePurchase(purchase: Purchase)
    fun reconnectToBillingService()
}