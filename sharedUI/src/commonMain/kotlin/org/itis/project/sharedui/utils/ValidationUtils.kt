package org.itis.project.sharedui.utils

fun isValidEmail(email: String): Boolean {
    if (email.isBlank()) return false
    val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
    return emailRegex.matches(email)
}


fun isValidUsername(username: String): Boolean {
    if (username.isBlank()) return false
    if (username.length < 3 || username.length > 20) return false
    val usernameRegex = Regex("^[a-zA-Z0-9_]+$")
    return usernameRegex.matches(username)
}

fun isValidPassword(password: String): Boolean {
    if (password.isBlank()) return false
    if (password.length < 6) return false
    val hasDigit = password.any { it.isDigit() }
    val hasLetter = password.any { it.isLetter() }
    return hasDigit && hasLetter
}