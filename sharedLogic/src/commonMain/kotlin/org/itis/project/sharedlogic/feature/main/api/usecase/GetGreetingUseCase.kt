package org.itis.project.sharedlogic.feature.main.api.usecase

interface GetGreetingUseCase {
    suspend operator fun invoke(): String
}