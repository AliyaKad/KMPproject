package org.itis.project.sharedlogic.domain.usecase

import org.itis.project.sharedlogic.feature.main.api.model.ApodModel
import org.itis.project.sharedlogic.feature.main.api.repository.NasaRepository
import org.itis.project.sharedlogic.feature.main.api.usecase.GetApodUseCase

internal class GetApodUseCaseImpl(
    private val repository: NasaRepository
): GetApodUseCase {
    override suspend fun invoke(): ApodModel {
        return repository.apod()
    }
}