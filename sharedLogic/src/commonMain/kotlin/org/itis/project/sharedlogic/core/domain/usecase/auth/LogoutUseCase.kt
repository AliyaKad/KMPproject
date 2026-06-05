package org.itis.project.domain

import org.itis.project.sharedlogic.feature.auth.impl.data.AuthRepository

class LogoutUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke() {
        authRepository.logout()
    }
}