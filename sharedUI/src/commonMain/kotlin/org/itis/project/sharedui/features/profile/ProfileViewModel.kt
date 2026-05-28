package org.itis.project.sharedui.features.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.itis.project.sharedlogic.domain.usecase.auth.GetThemeUseCase
import org.itis.project.sharedlogic.domain.usecase.auth.GetCurrentUserUseCase
import org.itis.project.sharedlogic.domain.usecase.auth.UpdateThemeUseCase

class ProfileViewModel(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val updateThemeUseCase: UpdateThemeUseCase,
    private val getThemeUseCase: GetThemeUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ProfileState())
    val state: StateFlow<ProfileState> = _state.asStateFlow()

    private val _isDarkTheme = MutableStateFlow(true)
    val isDarkTheme: StateFlow<Boolean> = _isDarkTheme.asStateFlow()

    init {
        loadProfile()
        loadTheme()
    }

    fun handleEvent(event: ProfileEvent) {
        when (event) {
            ProfileEvent.LoadProfile -> loadProfile()
            is ProfileEvent.UpdateTheme -> updateTheme(event.isDarkTheme)
        }
    }

    private fun loadProfile() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, errorMessage = null)
            try {
                val user = getCurrentUserUseCase()
                _state.value = ProfileState(
                    isLoading = false,
                    user = user,
                    errorMessage = null
                )
            } catch (e: Exception) {
                _state.value = ProfileState(
                    isLoading = false,
                    user = null,
                    errorMessage = e.message ?: "Ошибка загрузки профиля"
                )
            }
        }
    }

    private fun loadTheme() {
        viewModelScope.launch {
            _isDarkTheme.value = getThemeUseCase() ?: true
        }
    }

    private fun updateTheme(isDarkTheme: Boolean) {
        viewModelScope.launch {
            updateThemeUseCase(isDarkTheme)
            _isDarkTheme.value = isDarkTheme
        }
    }
}