package org.itis.project.sharedlogic.analytics

interface AnalyticsService {
    fun logEvent(eventName: String, params: Map<String, String> = emptyMap())
}