package org.itis.project.sharedlogic.feature.profile.api.domain

interface UpdateThemeUseCase {
    suspend operator fun invoke(isDarkTheme: Boolean)
}