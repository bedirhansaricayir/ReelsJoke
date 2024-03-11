package com.reelsjoke.app.core.extensions

import android.util.Log
import com.reelsjoke.app.domain.model.AnalyticsEvent
import com.reelsjoke.app.domain.repository.AnalyticsHelper

fun AnalyticsHelper.onBalloonClicked() =
    logEvent(
        AnalyticsEvent(
            type = "on_balloon_clicked"
        )
    )

fun AnalyticsHelper.setPremiumUser() =
    logEvent(
        AnalyticsEvent(
            type = "on_premium_user_is_set",
        )
    )

 fun <T> AnalyticsHelper.logEffectTriggered(screenName: String,effect: T) =
    logEvent(
        AnalyticsEvent(
            type = "${screenName}_ui_effect_triggered",
            extras = listOf(
                AnalyticsEvent.Param(
                    key = "effect_name",
                    value = effect.toString()
                )
            )
        )
    )
