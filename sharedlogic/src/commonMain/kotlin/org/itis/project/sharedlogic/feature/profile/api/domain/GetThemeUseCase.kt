package org.itis.project.sharedlogic.feature.profile.api.domain

interface GetThemeUseCase {
    suspend operator fun invoke(): Boolean
}