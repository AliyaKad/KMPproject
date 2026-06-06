package org.itis.project.sharedlogic.feature.profile.impl.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.itis.project.sharedlogic.analytics.AnalyticsService
import org.itis.project.sharedlogic.feature.profile.api.domain.GetCurrentUserUseCase
import org.itis.project.sharedlogic.feature.profile.impl.data.ThemeManager
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ProfileViewModel(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val themeManager: ThemeManager
) : ViewModel(), KoinComponent {

    private val analyticsService: AnalyticsService by inject()

    fun onScreenOpen() {
        analyticsService.logEvent("screen_open", mapOf("screen_name" to "profile"))
    }

    private val _state = MutableStateFlow(ProfileState())
    val state: StateFlow<ProfileState> = _state.asStateFlow()

    val isDarkTheme: StateFlow<Boolean> = themeManager.isDarkTheme

    init {
        loadProfile()
    }

    fun handleEvent(event: ProfileEvent) {
        when (event) {
            ProfileEvent.LoadProfile -> loadProfile()
            is ProfileEvent.UpdateTheme -> themeManager.setDarkTheme(event.isDarkTheme)
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
}
