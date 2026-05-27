package org.itis.project.sharedlogic.feature.auth

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.itis.project.sharedlogic.feature.auth.impl.data.UserPreferences

class AndroidUserPreferences(private val context: Context) : UserPreferences {
    private val prefs: SharedPreferences = context.getSharedPreferences("spacevue_prefs", Context.MODE_PRIVATE)

    override suspend fun saveCurrentUserId(userId: Long) {
        withContext(Dispatchers.IO) {
            prefs.edit().putLong("current_user_id", userId).apply()
        }
    }

    override suspend fun getCurrentUserId(): Long? {
        return withContext(Dispatchers.IO) {
            val id = prefs.getLong("current_user_id", -1L)
            if (id != -1L) id else null
        }
    }

    override suspend fun clearCurrentUserId() {
        withContext(Dispatchers.IO) {
            prefs.edit().remove("current_user_id").apply()
        }
    }
}