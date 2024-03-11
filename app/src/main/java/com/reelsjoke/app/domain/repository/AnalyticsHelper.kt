package com.reelsjoke.app.domain.repository

import com.reelsjoke.app.domain.model.AnalyticsEvent

interface AnalyticsHelper {
    fun logEvent(event: AnalyticsEvent)
}