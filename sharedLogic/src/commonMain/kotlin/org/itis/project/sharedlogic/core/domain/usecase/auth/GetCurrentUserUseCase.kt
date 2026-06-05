package org.itis.project.sharedlogic.core.domain.usecase.auth

import org.itis.project.domain.User
import org.itis.project.sharedlogic.feature.auth.impl.data.AuthRepository

class GetCurrentUserUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): User? {
        return authRepository.checkAuth()
    }
}