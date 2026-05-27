package org.itis.project.sharedlogic.di

import app.cash.sqldelight.db.SqlDriver
import org.itis.project.sharedlogic.feature.auth.impl.data.UserPreferences

interface PlatformDependencies {
    fun createDriver(): SqlDriver
    fun createUserPreferences(): UserPreferences
}