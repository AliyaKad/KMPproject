package org.itis.project.sharedlogic.feature.auth.impl.data

import org.itis.project.Database
import org.itis.project.domain.Session
import org.itis.project.domain.User

class UserDao(private val db: Database) {

    suspend fun getUserByEmail(email: String): User? {
        return db.databaseQueries.selectUserByEmail(email)
            .executeAsOneOrNull()
            ?.let { query ->
                User(
                    id = query.id,
                    email = query.email,
                    username = query.username,
                    passwordHash = query.password_hash,
                    avatarColor = query.avatar_color
                )
            }
    }

    suspend fun getUserById(userId: Long): User? {
        return db.databaseQueries.selectUserById(userId)
            .executeAsOneOrNull()
            ?.let { query ->
                User(
                    id = query.id,
                    email = query.email,
                    username = query.username,
                    passwordHash = query.password_hash,
                    avatarColor = query.avatar_color
                )
            }
    }

    suspend fun insertUser(
        email: String,
        username: String,
        passwordHash: String,
        avatarColor: String
    ): Long {
        db.databaseQueries.insertUser(
            email = email,
            username = username,
            password_hash = passwordHash,
            avatar_color = avatarColor
        )

        return 0L
    }

    suspend fun saveSession(userId: Long) {
        val now = System.currentTimeMillis().toString()
        db.databaseQueries.insertOrReplaceSession(
            user_id = userId,
            last_login = now
        )
    }

    suspend fun getSession(userId: Long): Session? {
        return db.databaseQueries.selectSession(userId)
            .executeAsOneOrNull()
            ?.let { query ->
                Session(
                    userId = query.user_id,
                    isLoggedIn = query.is_logged_in == 1L,
                    lastLogin = query.last_login
                )
            }
    }

    suspend fun clearSession(userId: Long) {
        db.databaseQueries.deleteSession(userId)
    }

    suspend fun clearAllSessions() {
        db.databaseQueries.clearAllSessions()
    }
}