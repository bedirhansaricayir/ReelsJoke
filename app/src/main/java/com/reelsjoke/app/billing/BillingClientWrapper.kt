package com.reelsjoke.app.billing


/**
 * Created by bedirhansaricayir on 22.02.2024.
 */
import android.app.Activity
import android.content.Context
import android.util.Log
import com.android.billingclient.api.AcknowledgePurchaseParams
import com.android.billingclient.api.AcknowledgePurchaseResponseListener
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClient.BillingResponseCode
import com.android.billingclient.api.BillingClient.ProductType
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.BillingFlowParams
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.ProductDetails
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.PurchasesUpdatedListener
import com.android.billingclient.api.QueryProductDetailsParams
import com.android.billingclient.api.queryProductDetails
import com.reelsjoke.app.domain.repository.IBillingClient
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


class BillingClientWrapper @Inject constructor(
    @ApplicationContext private val context: Context
) : PurchasesUpdatedListener, IBillingClient {

    private val TAG = "BillingClient"
    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private val _statusText = MutableStateFlow("Initializing...")

    override fun status(): StateFlow<String> =
        _statusText.asStateFlow()


    private val billingClient = BillingClient
        .newBuilder(context)
        .enablePendingPurchases()
        .setListener(this)
        .build()

    override fun onPurchasesUpdated(
        billingResult: BillingResult,
        purchaseList: MutableList<Purchase>?
    ) {
        when (billingResult.responseCode) {
            BillingResponseCode.OK -> {
                if (purchaseList != null) {
                    for (purchase in purchaseList) {
                        completePurchase(purchase)

                    }
                }
            }

            BillingResponseCode.USER_CANCELED -> _statusText.value = "Purchase Canceled"
            BillingResponseCode.ITEM_ALREADY_OWNED -> _statusText.value = "Product Available"
        }
    }

    override fun initBilling() {
        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                if (billingResult.responseCode == BillingResponseCode.OK) {
                    coroutineScope.launch {
                        queryProducts()
                    }
                }
            }

            override fun onBillingServiceDisconnected() {
                reconnectToBillingService()
            }
        })
    }

    override suspend fun queryProducts() {
        val productList = ArrayList<QueryProductDetailsParams.Product>()
        productList.add(
            QueryProductDetailsParams.Product.newBuilder()
                .setProductId("product_id")
                .setProductType(ProductType.INAPP)
                .build()
        )
        val params = QueryProductDetailsParams.newBuilder()
        params.setProductList(productList).build()

        val productDetailsResult = withContext(Dispatchers.IO) {
            billingClient.queryProductDetails(params.build())
        }
        productDetailsResult.productDetailsList?.let { productDetailsList ->
            makePurchase(productDetailsList)
        }
    }

    override fun makePurchase(productDetailsList: List<ProductDetails>) {
        val productDetailsParamsList = listOf(
            BillingFlowParams.ProductDetailsParams.newBuilder()
                .setProductDetails(productDetailsList[0])
                .build()
        )
        val billingFlowParams = BillingFlowParams.newBuilder()
            .setProductDetailsParamsList(productDetailsParamsList)
            .build()

        launchBillingFlow(context, billingFlowParams)
    }

    override fun launchBillingFlow(context: Context, billingFlowParams: BillingFlowParams) {
        billingClient.launchBillingFlow(context as Activity, billingFlowParams)
    }

    private val acknowledgePurchaseListener = AcknowledgePurchaseResponseListener { billingResult ->
        if (billingResult.responseCode == BillingResponseCode.OK) {
            billingClient.endConnection()
        }
    }

    override fun completePurchase(purchase: Purchase) {
        if (purchase.purchaseState == Purchase.PurchaseState.PURCHASED) {
            if (!purchase.isAcknowledged) {
                _statusText.value = "Purchase Completed"
                val acknowledgePurchaseParams = AcknowledgePurchaseParams.newBuilder()
                    .setPurchaseToken(purchase.purchaseToken)
                    .build()
                coroutineScope.launch {
                    billingClient.acknowledgePurchase(
                        acknowledgePurchaseParams,
                        acknowledgePurchaseListener
                    )
                }
            }
        }
    }

    override fun reconnectToBillingService() {
        if (!billingClient.isReady()) {
            billingClient.startConnection(object : BillingClientStateListener {

                override fun onBillingSetupFinished(billingResult: BillingResult) {
                    if (billingResult.responseCode == BillingResponseCode.OK) {
                        Log.d(TAG, "Bağlantı tekrar kuruldu.")
                    } else {
                        Log.e(TAG, "Bağlantı tekrar kurulamadı: ${billingResult.responseCode}")
                    }
                }

                override fun onBillingServiceDisconnected() {
                    Log.d(TAG, "Bağlantı kesildi. Tekrar bağlanmaya çalışılıyor...")
                    reconnectToBillingService()
                }
            })
        }
    }
}