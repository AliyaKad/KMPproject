package org.itis.project.domain

import org.itis.project.sharedlogic.core.data.repository.auth.AuthRepository

class LogoutUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke() {
        authRepository.logout()
    }
}