package org.itis.project.sharedlogic.core.di

import org.koin.core.module.Module
import org.koin.dsl.module
import org.itis.project.sharedlogic.analytics.AnalyticsService
import org.itis.project.sharedlogic.analytics.DesktopAnalyticsService

actual val analyticsModule: Module = module {
    single<AnalyticsService> { DesktopAnalyticsService() }
}