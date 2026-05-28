package org.itis.project.sharedlogic.feature.auth

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.itis.project.sharedlogic.feature.auth.impl.data.UserPreferences
import java.io.File

class DesktopUserPreferences : UserPreferences {
    private val prefsFile = File(System.getProperty("user.home"), ".spacevue_prefs.json")

    @Serializable
    private data class PrefsData(
        val currentUserId: Long? = null,
        val isDarkTheme: Boolean? = null
    )

    private fun readPrefs(): PrefsData {
        return if (prefsFile.exists()) {
            try {
                Json.decodeFromString<PrefsData>(prefsFile.readText())
            } catch (e: Exception) {
                PrefsData()
            }
        } else {
            PrefsData()
        }
    }

    private fun writePrefs(data: PrefsData) {
        prefsFile.writeText(Json.encodeToString(data))
    }

    override suspend fun saveCurrentUserId(userId: Long) {
        withContext(Dispatchers.IO) {
            val current = readPrefs()
            writePrefs(current.copy(currentUserId = userId))
        }
    }

    override suspend fun getCurrentUserId(): Long? {
        return withContext(Dispatchers.IO) {
            readPrefs().currentUserId
        }
    }

    override suspend fun clearCurrentUserId() {
        withContext(Dispatchers.IO) {
            val current = readPrefs()
            writePrefs(current.copy(currentUserId = null))
        }
    }

    override suspend fun saveThemePreference(isDarkTheme: Boolean) {
        withContext(Dispatchers.IO) {
            val current = readPrefs()
            writePrefs(current.copy(isDarkTheme = isDarkTheme))
        }
    }

    override suspend fun getThemePreference(): Boolean? {
        return withContext(Dispatchers.IO) {
            readPrefs().isDarkTheme
        }
    }
}