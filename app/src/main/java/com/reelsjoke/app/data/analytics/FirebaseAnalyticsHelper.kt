package com.reelsjoke.app.data.analytics

import android.util.Log
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.logEvent
import com.reelsjoke.app.domain.model.AnalyticsEvent
import com.reelsjoke.app.domain.repository.AnalyticsHelper
import javax.inject.Inject

/**
 * Implementation of `AnalyticsHelper` which logs events
 * to a Firebase backend.
 */
class FirebaseAnalyticsHelper @Inject constructor(
    private val firebaseAnalytics: FirebaseAnalytics,
) : AnalyticsHelper {

    override fun logEvent(event: AnalyticsEvent) {
        firebaseAnalytics.logEvent(event.type) {
            Log.d("logging",event.type)
            for (extra in event.extras) {
                // Truncate parameter keys and values
                // according to firebase maximum length values.
                Log.d("param",extra.value.toString())

                param(
                    key = extra.key.take(40),
                    value = extra.value.take(100),
                )
            }
        }
    }
}
