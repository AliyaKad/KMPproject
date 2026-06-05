package org.itis.project.sharedlogic.feature.profile.impl.domain

import org.itis.project.sharedlogic.feature.auth.impl.data.UserPreferences
import org.itis.project.sharedlogic.feature.profile.api.domain.GetThemeUseCase


class GetThemeUseCaseImpl(
    private val userPreferences: UserPreferences
) : GetThemeUseCase {
    override suspend operator fun invoke(): Boolean {
        return userPreferences.getThemePreference() ?: false
    }
}