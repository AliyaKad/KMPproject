package org.itis.project.sharedlogic.core.domain.usecase.auth

import org.itis.project.sharedlogic.feature.auth.impl.data.UserPreferences

class GetThemeUseCase(
    private val userPreferences: UserPreferences
) {
    suspend operator fun invoke(): Boolean? {
        return userPreferences.getThemePreference()
    }
}

