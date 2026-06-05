package org.itis.project.sharedlogic.analytics

import android.content.Context
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import kotlin.getValue

class AndroidAnalyticsService(
    private val context: Context
) : AnalyticsService {

    private val firebaseAnalytics: FirebaseAnalytics by lazy {
        FirebaseAnalytics.getInstance(context)
    }

    override fun logEvent(eventName: String, params: Map<String, String>) {
        try {
            val bundle = Bundle().apply {
                params.forEach { (key, value) -> putString(key, value) }
            }
            firebaseAnalytics.logEvent(eventName, bundle)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}