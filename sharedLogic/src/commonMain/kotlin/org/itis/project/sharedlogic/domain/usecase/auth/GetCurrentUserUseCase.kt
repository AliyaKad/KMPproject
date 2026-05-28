package org.itis.project.sharedlogic.domain.usecase.auth

import org.itis.project.domain.User
import org.itis.project.sharedlogic.data.repository.auth.AuthRepository

class GetCurrentUserUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): User? {
        return authRepository.checkAuth()
    }
}