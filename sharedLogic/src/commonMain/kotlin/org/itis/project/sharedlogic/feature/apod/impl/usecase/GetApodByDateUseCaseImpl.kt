package org.itis.project.sharedlogic.feature.apod.impl.usecase

import org.itis.project.sharedlogic.feature.apod.api.usecase.GetApodByDateUseCase
import org.itis.project.sharedlogic.feature.main.api.model.ApodModel
import org.itis.project.sharedlogic.feature.main.api.repository.NasaRepository

class GetApodByDateUseCaseImpl(
    private val repository: NasaRepository
) : GetApodByDateUseCase {
    override suspend fun invoke(date: String): ApodModel = repository.apod(date)
}