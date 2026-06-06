package org.itis.project.sharedlogic.feature.main.api.usecase

import org.itis.project.sharedlogic.feature.main.api.model.ApodModel

interface GetApodUseCase {
    suspend operator fun invoke(): ApodModel
}