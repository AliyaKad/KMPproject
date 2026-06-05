package org.itis.project.sharedlogic.feature.auth.api.domain

interface LogoutUseCase {
    suspend operator fun invoke()
}