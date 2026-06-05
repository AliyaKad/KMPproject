package org.itis.project.sharedlogic.feature.auth.api.domain

import org.itis.project.domain.User

interface CheckAuthUseCase {
    suspend operator fun invoke(): User?
}