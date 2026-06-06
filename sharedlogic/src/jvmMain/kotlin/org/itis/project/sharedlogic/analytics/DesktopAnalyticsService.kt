package org.itis.project.sharedlogic.analytics

class DesktopAnalyticsService : AnalyticsService {
    override fun logEvent(eventName: String, params: Map<String, String>) {
        println("Analytics - $eventName: $params")
    }
}