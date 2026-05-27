package org.itis.project.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.itis.project.domain.CheckAuthUseCase
import org.itis.project.domain.LoginUseCase
import org.itis.project.domain.LogoutUseCase
import org.itis.project.domain.RegisterUseCase

class AuthViewModel(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase,
    private val checkAuthUseCase: CheckAuthUseCase,
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<AuthState>(AuthState.Loading)
    val state: StateFlow<AuthState> = _state.asStateFlow()

    init {
        checkAuth()
    }

    fun handleEvent(event: AuthEvent) {
        when (event) {
            is AuthEvent.OnLogin -> login(event.email, event.password)
            is AuthEvent.OnRegister -> register(event.email, event.username, event.password)
            is AuthEvent.OnLogout -> logout()
            is AuthEvent.CheckAuth -> checkAuth()
        }
    }

    private fun checkAuth() {
        viewModelScope.launch {
            _state.value = AuthState.Loading
            try {
                val user = checkAuthUseCase()
                _state.value = if (user != null) {
                    AuthState.Authorized(user)
                } else {
                    AuthState.Unauthorized
                }
            } catch (e: Exception) {
                _state.value = AuthState.Error(e.message ?: "Unknown error")
            }
        }
    }

    private fun login(email: String, password: String) {
        viewModelScope.launch {
            _state.value = AuthState.Loading
            val result = loginUseCase(email, password)
            _state.value = result.fold(
                onSuccess = { AuthState.Authorized(it) },
                onFailure = { AuthState.Error(it.message ?: "Login failed") }
            )
        }
    }

    private fun register(email: String, username: String, password: String) {
        viewModelScope.launch {
            _state.value = AuthState.Loading
            val result = registerUseCase(email, username, password)
            _state.value = result.fold(
                onSuccess = { AuthState.Authorized(it) },
                onFailure = { AuthState.Error(it.message ?: "Registration failed") }
            )
        }
    }

    private fun logout() {
        viewModelScope.launch {
            logoutUseCase()
            _state.value = AuthState.Unauthorized
        }
    }
}