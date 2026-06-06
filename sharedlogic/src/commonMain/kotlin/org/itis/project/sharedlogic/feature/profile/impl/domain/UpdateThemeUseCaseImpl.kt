package org.itis.project.sharedlogic.feature.profile.impl.domain

import org.itis.project.sharedlogic.feature.auth.impl.data.UserPreferences
import org.itis.project.sharedlogic.feature.profile.api.domain.UpdateThemeUseCase


class UpdateThemeUseCaseImpl(
    private val userPreferences: UserPreferences
) : UpdateThemeUseCase {
    override suspend operator fun invoke(isDarkTheme: Boolean) {
        userPreferences.saveThemePreference(isDarkTheme)
    }
}