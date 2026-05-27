package org.itis.project.presentation

import org.itis.project.domain.User


sealed class AuthState {
    object Loading : AuthState()
    object Unauthorized : AuthState()
    data class Authorized(val user: User) : AuthState()
    data class Error(val message: String) : AuthState()
}

sealed class AuthEvent {
    data class OnLogin(val email: String, val password: String) : AuthEvent()
    data class OnRegister(val email: String, val username: String, val password: String) : AuthEvent()
    object OnLogout : AuthEvent()
    object CheckAuth : AuthEvent()
}