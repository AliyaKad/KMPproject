package org.itis.project.sharedlogic.feature.auth.impl.domain

import org.itis.project.domain.User
import org.itis.project.sharedlogic.feature.auth.api.domain.CheckAuthUseCase
import org.itis.project.sharedlogic.feature.auth.impl.data.AuthRepository


class CheckAuthUseCaseImpl(
    private val authRepository: AuthRepository
) : CheckAuthUseCase {
    override suspend operator fun invoke(): User? {
        return authRepository.checkAuth()
    }
}