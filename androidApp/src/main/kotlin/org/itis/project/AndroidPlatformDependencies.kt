package org.itis.project

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import org.itis.project.sharedlogic.core.di.PlatformDependencies
import org.itis.project.sharedlogic.feature.auth.AndroidUserPreferences
import org.itis.project.sharedlogic.feature.auth.impl.data.UserPreferences

class AndroidPlatformDependencies(
    private val context: Context
) : PlatformDependencies {

    override fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(
            schema = Database.Schema,
            context = context,
            name = "spacevue.db"
        )
    }

    override fun createUserPreferences(): UserPreferences {
        return AndroidUserPreferences(context)
    }
}

