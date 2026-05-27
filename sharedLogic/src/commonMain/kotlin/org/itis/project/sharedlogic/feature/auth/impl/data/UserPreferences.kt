package org.itis.project.sharedlogic.feature.auth.impl.data

interface UserPreferences {
    suspend fun saveCurrentUserId(userId: Long)
    suspend fun getCurrentUserId(): Long?
    suspend fun clearCurrentUserId()
}