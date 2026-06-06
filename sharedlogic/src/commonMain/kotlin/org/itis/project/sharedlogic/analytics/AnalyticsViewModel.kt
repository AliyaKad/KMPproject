package org.itis.project.sharedlogic.analytics

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.itis.project.sharedlogic.core.viewmodel.BaseViewModel

abstract class AnalyticsViewModel<State : Any, Intent : Any, Effect : Any>(
    initialState: State
) : BaseViewModel<State, Intent, Effect>(initialState), KoinComponent {

    protected val analyticsService: AnalyticsService by inject()

    protected fun logScreenOpen(screenName: String) {
        analyticsService.logEvent("screen_open", mapOf("screen_name" to screenName))
    }

    fun logEvent(eventName: String, params: Map<String, String> = emptyMap()) {
        analyticsService.logEvent(eventName, params)
    }
}