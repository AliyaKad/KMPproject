package org.itis.project.sharedlogic.feature.auth.impl.domain

import org.itis.project.domain.User
import org.itis.project.sharedlogic.feature.auth.api.domain.RegisterUseCase
import org.itis.project.sharedlogic.feature.auth.impl.data.AuthRepository

class RegisterUseCaseImpl(
    private val authRepository: AuthRepository
) : RegisterUseCase {
    override suspend operator fun invoke(email: String, username: String, password: String): Result<User> {
        return authRepository.register(email, username, password)
    }
}