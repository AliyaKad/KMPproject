package org.itis.project.domain

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Long,
    val email: String,
    val username: String,
    val passwordHash: String,
    val avatarColor: String
)

@Serializable
data class Session(
    val userId: Long,
    val isLoggedIn: Boolean,
    val lastLogin: String
)

fun generateAvatarColor(): String {
    val colors = listOf(
        "#FF5252", "#FF4081", "#E040FB", "#7C4DFF",
        "#536DFE", "#448AFF", "#40C4FF", "#18FFFF",
        "#64FFDA", "#69F0AE", "#B2FF59", "#EEFF41",
        "#FFFF00", "#FFD740", "#FFAB40", "#FF6E40"
    )
    return colors.random()
}

fun hashPassword(password: String): String {
    return password.toByteArray().joinToString("") { "%02x".format(it) }
}