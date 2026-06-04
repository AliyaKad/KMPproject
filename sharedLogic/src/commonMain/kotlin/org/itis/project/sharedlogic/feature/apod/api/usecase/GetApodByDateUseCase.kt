package org.itis.project.sharedlogic.feature.apod.api.usecase

import org.itis.project.sharedlogic.feature.main.api.model.ApodModel

interface GetApodByDateUseCase {
    suspend operator fun invoke(date: String): ApodModel
}