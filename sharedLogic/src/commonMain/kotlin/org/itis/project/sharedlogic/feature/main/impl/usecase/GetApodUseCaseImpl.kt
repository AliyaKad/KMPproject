package org.itis.project.sharedlogic.domain.usecase

import org.itis.project.sharedlogic.feature.main.api.model.Apod
import org.itis.project.sharedlogic.feature.main.api.repository.NasaRepository
import org.itis.project.sharedlogic.feature.main.api.usecase.GetApodUseCase

class GetApodUseCaseImpl(
    private val repository: NasaRepository
): GetApodUseCase {
    override suspend fun invoke(): Apod {
        return repository.apod()
    }
}