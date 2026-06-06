package org.itis.project.sharedlogic.feature.auth.impl.domain

import org.itis.project.sharedlogic.feature.auth.api.domain.LogoutUseCase
import org.itis.project.sharedlogic.feature.auth.impl.data.AuthRepository

class LogoutUseCaseImpl(
    private val authRepository: AuthRepository
) : LogoutUseCase {
    override suspend operator fun invoke() {
        authRepository.logout()
    }
}