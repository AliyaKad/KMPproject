package org.itis.project.sharedlogic.feature.auth

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.itis.project.sharedlogic.feature.auth.impl.data.UserPreferences

class AndroidUserPreferences(private val context: Context) : UserPreferences {
    private val prefs: SharedPreferences = context.getSharedPreferences("spacevue_prefs", Context.MODE_PRIVATE)
    private companion object {
        const val KEY_CURRENT_USER_ID = "current_user_id"
        const val KEY_THEME_DARK = "theme_dark"
    }

    override suspend fun saveCurrentUserId(userId: Long) {
        withContext(Dispatchers.IO) {
            prefs.edit().putLong(KEY_CURRENT_USER_ID, userId).apply()
        }
    }

    override suspend fun getCurrentUserId(): Long? {
        return withContext(Dispatchers.IO) {
            val id = prefs.getLong(KEY_CURRENT_USER_ID, -1L)
            if (id != -1L) id else null
        }
    }

    override suspend fun clearCurrentUserId() {
        withContext(Dispatchers.IO) {
            prefs.edit().remove(KEY_CURRENT_USER_ID).apply()
        }
    }

    override suspend fun saveThemePreference(isDarkTheme: Boolean) {
        withContext(Dispatchers.IO) {
            prefs.edit().putBoolean(KEY_THEME_DARK, isDarkTheme).apply()
        }
    }

    override suspend fun getThemePreference(): Boolean? {
        return withContext(Dispatchers.IO) {
            if (prefs.contains(KEY_THEME_DARK)) prefs.getBoolean(KEY_THEME_DARK, true) else null
        }
    }
}