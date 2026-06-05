package org.itis.project.sharedlogic.core.di

import org.itis.project.Database
import org.itis.project.sharedlogic.feature.auth.impl.data.UserPreferences
import org.koin.dsl.module

fun createAppModule(dependencies: PlatformDependencies) = module {
    single { Database(dependencies.createDriver()) }
    single<UserPreferences> { dependencies.createUserPreferences() }
}