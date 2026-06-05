package org.itis.project.domain

import org.itis.project.sharedlogic.feature.auth.impl.data.AuthRepository

class LoginUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Result<User> {
        return authRepository.login(email, password)
    }
}