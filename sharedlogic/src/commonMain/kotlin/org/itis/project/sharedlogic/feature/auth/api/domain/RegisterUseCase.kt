package org.itis.project.sharedlogic.feature.auth.api.domain

import org.itis.project.domain.User

interface RegisterUseCase {
    suspend operator fun invoke(email: String, username: String, password: String): Result<User>
}