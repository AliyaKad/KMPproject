package org.itis.project.sharedlogic.feature.profile.impl.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.itis.project.sharedlogic.feature.auth.impl.data.UserPreferences

/**
 * Единый источник правды для темы приложения.
 *
 * Используется в `App.kt` (через `koinInject()`) для определения исходной темы,
 * и в `ProfileViewModel` для переключения. Изменения сразу видны в обоих местах,
 * без необходимости разделять состояние через несколько ViewModel'ов.
 */
class ThemeManager(
    private val userPreferences: UserPreferences,
    private val defaultDarkTheme: Boolean = true
) {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    private val _isDarkTheme = MutableStateFlow(defaultDarkTheme)
    val isDarkTheme: StateFlow<Boolean> = _isDarkTheme.asStateFlow()

    init {
        scope.launch {
            _isDarkTheme.value = userPreferences.getThemePreference() ?: defaultDarkTheme
        }
    }

    fun setDarkTheme(isDark: Boolean) {
        _isDarkTheme.value = isDark
        scope.launch {
            userPreferences.saveThemePreference(isDark)
        }
    }
}
