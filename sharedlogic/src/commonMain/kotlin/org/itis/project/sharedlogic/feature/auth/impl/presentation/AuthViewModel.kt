package org.itis.project.sharedlogic.feature.auth.impl.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.itis.project.sharedlogic.analytics.AnalyticsService
import org.itis.project.sharedlogic.feature.auth.api.domain.CheckAuthUseCase
import org.itis.project.sharedlogic.feature.auth.api.domain.LoginUseCase
import org.itis.project.sharedlogic.feature.auth.api.domain.LogoutUseCase
import org.itis.project.sharedlogic.feature.auth.api.domain.RegisterUseCase
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AuthViewModel(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase,
    private val checkAuthUseCase: CheckAuthUseCase,
    private val logoutUseCase: LogoutUseCase
) : ViewModel(), KoinComponent {

    private val analyticsService: AnalyticsService by inject()

    private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    private val _state = MutableStateFlow<AuthState>(AuthState.Loading)
    val state: StateFlow<AuthState> = _state.asStateFlow()

    init {
        checkAuth()
    }

    fun onLoginScreenOpen() {
        analyticsService.logEvent("screen_open", mapOf("screen_name" to "login"))
        _uiState.update { AuthUiState.Idle }
    }

    fun onRegisterScreenOpen() {
        analyticsService.logEvent("screen_open", mapOf("screen_name" to "register"))
        _uiState.update { AuthUiState.Idle }
    }

    fun handleEvent(event: AuthEvent) {
        when (event) {
            is AuthEvent.OnLogin -> login(event.email, event.password)
            is AuthEvent.OnRegister -> register(event.email, event.username, event.password)
            AuthEvent.OnLogout -> logout()
            AuthEvent.CheckAuth -> checkAuth()
            AuthEvent.ClearError -> clearError()
        }
    }

    private fun checkAuth() {
        viewModelScope.launch {
            _state.value = AuthState.Loading
            _state.value = try {
                checkAuthUseCase()?.let { AuthState.Authorized(it) } ?: AuthState.Unauthorized
            } catch (e: Exception) {
                AuthState.Error(e.message ?: "Unknown error")
            }
        }
    }

    private fun login(email: String, password: String) {
        viewModelScope.launch {
            _uiState.update { AuthUiState.Loading }

            val result = try {
                loginUseCase(email, password)
            } catch (e: Exception) {
                Result.failure(e)
            }

            result.fold(
                onSuccess = { user ->
                    _state.value = AuthState.Authorized(user)
                    _uiState.update { AuthUiState.Success("Вход выполнен успешно") }
                },
                onFailure = { error ->
                    val errorMessage = when {
                        error.message?.contains("user not found", ignoreCase = true) == true ->
                            "Пользователь с таким email не найден"
                        error.message?.contains("wrong password", ignoreCase = true) == true ->
                            "Неверный пароль"
                        else -> error.message ?: "Ошибка входа. Попробуйте позже"
                    }
                    _uiState.update { AuthUiState.Error(errorMessage) }
                }
            )
        }
    }

    private fun register(email: String, username: String, password: String) {
        viewModelScope.launch {
            _uiState.update { AuthUiState.Loading }

            val result = try {
                registerUseCase(email, username, password)
            } catch (e: Exception) {
                Result.failure(e)
            }

            result.fold(
                onSuccess = { user ->
                    _state.value = AuthState.Authorized(user)
                    _uiState.update { AuthUiState.Success("Регистрация прошла успешно") }
                },
                onFailure = { error ->
                    val errorMessage = when {
                        error.message?.contains("email already exists", ignoreCase = true) == true ->
                            "Пользователь с таким email уже существует"
                        error.message?.contains("username already exists", ignoreCase = true) == true ->
                            "Пользователь с таким именем уже существует"
                        else -> error.message ?: "Ошибка регистрации. Попробуйте позже"
                    }
                    _uiState.update { AuthUiState.Error(errorMessage) }
                }
            )
        }
    }

    private fun logout() {
        viewModelScope.launch {
            logoutUseCase()
            _state.value = AuthState.Unauthorized
            _uiState.update { AuthUiState.Idle }
        }
    }

    private fun clearError() {
        _uiState.update { AuthUiState.Idle }
    }
}

sealed class AuthUiState {
    data object Idle : AuthUiState()
    data object Loading : AuthUiState()
    data class Success(val message: String) : AuthUiState()
    data class Error(val message: String) : AuthUiState()
}