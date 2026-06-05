package org.itis.project.domain

import org.itis.project.sharedlogic.feature.auth.impl.data.AuthRepository

class RegisterUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, username: String, password: String): Result<User> {
        return authRepository.register(email, username, password)
    }
}