package org.itis.project.sharedlogic.domain.usecase.auth

import org.itis.project.sharedlogic.feature.auth.impl.data.UserPreferences


class UpdateThemeUseCase(
    private val userPreferences: UserPreferences
) {
    suspend operator fun invoke(isDarkTheme: Boolean) {
        userPreferences.saveThemePreference(isDarkTheme)
    }
}