package org.itis.project.sharedui.features.auth

import org.itis.project.domain.User

sealed class AuthState {
    data object Loading : AuthState()
    data object Unauthorized : AuthState()
    data class Authorized(val user: User) : AuthState()
    data class Error(val message: String) : AuthState()
}

sealed class AuthEvent {
    data class OnLogin(val email: String, val password: String) : AuthEvent()
    data class OnRegister(val email: String, val username: String, val password: String) : AuthEvent()
    data object OnLogout : AuthEvent()
    data object CheckAuth : AuthEvent()
}

