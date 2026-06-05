package org.itis.project.sharedlogic.feature.auth.api.domain

import org.itis.project.domain.User


interface LoginUseCase {
    suspend operator fun invoke(email: String, password: String): Result<User>
}