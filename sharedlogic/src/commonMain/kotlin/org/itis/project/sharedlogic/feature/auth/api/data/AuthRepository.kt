package org.itis.project.sharedlogic.feature.auth.api.data

import org.itis.project.domain.User

interface AuthRepository {
    suspend fun login(email: String, password: String): Result<User>
    suspend fun register(email: String, username: String, password: String): Result<User>
    suspend fun checkAuth(): User?
    suspend fun logout()
}