package org.itis.project

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import org.itis.project.sharedlogic.Database
import org.itis.project.sharedlogic.core.di.PlatformDependencies
import org.itis.project.sharedlogic.feature.auth.DesktopUserPreferences
import org.itis.project.sharedlogic.feature.auth.impl.data.UserPreferences

class DesktopPlatformDependencies : PlatformDependencies {

    override fun createDriver(): SqlDriver {
        val driver = JdbcSqliteDriver(url = "jdbc:sqlite:spacevue.db")
        Database.Schema.create(driver)
        return driver
    }

    override fun createUserPreferences(): UserPreferences {
        return DesktopUserPreferences()
    }
}

