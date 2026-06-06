package org.itis.project.sharedlogic.feature.auth.impl.domain

import org.itis.project.domain.User
import org.itis.project.sharedlogic.feature.auth.api.domain.LoginUseCase
import org.itis.project.sharedlogic.feature.auth.impl.data.AuthRepository


class LoginUseCaseImpl(
    private val authRepository: AuthRepository
) : LoginUseCase {
    override suspend operator fun invoke(email: String, password: String): Result<User> {
        return authRepository.login(email, password)
    }
}