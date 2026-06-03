package org.itis.project.domain

import org.itis.project.sharedlogic.core.data.repository.auth.AuthRepository

class LoginUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Result<User> {
        return authRepository.login(email, password)
    }
}