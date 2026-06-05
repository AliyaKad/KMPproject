package org.itis.project.sharedlogic.feature.profile.impl.domain

import org.itis.project.domain.User
import org.itis.project.sharedlogic.feature.auth.impl.data.AuthRepository
import org.itis.project.sharedlogic.feature.profile.api.domain.GetCurrentUserUseCase

class GetCurrentUserUseCaseImpl(
    private val authRepository: AuthRepository
) : GetCurrentUserUseCase {
    override suspend operator fun invoke(): User? {
        return authRepository.checkAuth()
    }
}
