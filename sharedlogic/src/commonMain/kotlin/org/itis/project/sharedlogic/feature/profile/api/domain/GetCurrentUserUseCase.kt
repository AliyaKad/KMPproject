package org.itis.project.sharedlogic.feature.profile.api.domain

import org.itis.project.domain.User

interface GetCurrentUserUseCase {
    suspend operator fun invoke(): User?
}