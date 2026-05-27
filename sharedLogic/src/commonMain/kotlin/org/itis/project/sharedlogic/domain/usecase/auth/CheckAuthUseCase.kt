package org.itis.project.domain

import org.itis.project.sharedlogic.data.repository.auth.AuthRepository


class CheckAuthUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): User? {
        return authRepository.checkAuth()
    }
}