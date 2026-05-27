package org.itis.project.sharedlogic.data.repository.auth

import org.itis.project.domain.User
import org.itis.project.domain.generateAvatarColor
import org.itis.project.domain.hashPassword
import org.itis.project.sharedlogic.feature.auth.impl.data.UserDao
import org.itis.project.sharedlogic.feature.auth.impl.data.UserPreferences

class AuthRepository(
    private val userDao: UserDao,
    private val userPreferences: UserPreferences
) {
    suspend fun login(email: String, password: String): Result<User> {
        val user = userDao.getUserByEmail(email)
        return if (user != null && user.passwordHash == hashPassword(password)) {
            userDao.saveSession(user.id)
            userPreferences.saveCurrentUserId(user.id)
            Result.success(user)
        } else {
            Result.failure(Exception("Invalid email or password"))
        }
    }

    suspend fun register(email: String, username: String, password: String): Result<User> {
        val existingUser = userDao.getUserByEmail(email)
        if (existingUser != null) {
            return Result.failure(Exception("User already exists"))
        }

        userDao.insertUser(
            email = email,
            username = username,
            passwordHash = hashPassword(password),
            avatarColor = generateAvatarColor()
        )

        val user = userDao.getUserByEmail(email)
        if (user != null) {
            userDao.saveSession(user.id)
            userPreferences.saveCurrentUserId(user.id)
            return Result.success(user)
        }

        return Result.failure(Exception("Registration failed"))
    }

    suspend fun checkAuth(): User? {
        val userId = userPreferences.getCurrentUserId() ?: return null
        val session = userDao.getSession(userId) ?: return null
        return if (session.isLoggedIn) {
            userDao.getUserById(userId)
        } else null
    }

    suspend fun logout() {
        val userId = userPreferences.getCurrentUserId() ?: return
        userDao.clearSession(userId)
        userPreferences.clearCurrentUserId()
    }
}