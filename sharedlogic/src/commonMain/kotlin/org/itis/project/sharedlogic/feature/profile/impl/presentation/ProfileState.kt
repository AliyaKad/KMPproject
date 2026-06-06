package org.itis.project.sharedlogic.feature.profile.impl.presentation

import org.itis.project.domain.User

data class ProfileState(
    val isLoading: Boolean = true,
    val user: User? = null,
    val errorMessage: String? = null
)

sealed class ProfileEvent {
    data object LoadProfile : ProfileEvent()
    data class UpdateTheme(val isDarkTheme: Boolean) : ProfileEvent()
}

