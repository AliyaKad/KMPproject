package org.itis.project.sharedlogic.core.di

import android.content.Context
import org.koin.core.module.Module
import org.koin.dsl.module
import org.itis.project.sharedlogic.analytics.AndroidAnalyticsService
import org.itis.project.sharedlogic.analytics.AnalyticsService

actual val analyticsModule: Module = module {
    single<AnalyticsService> {
        val context: Context = get()
        AndroidAnalyticsService(context)
    }
}