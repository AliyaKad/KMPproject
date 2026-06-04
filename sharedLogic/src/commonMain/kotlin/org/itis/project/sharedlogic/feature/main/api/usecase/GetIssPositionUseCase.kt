package org.itis.project.sharedlogic.feature.main.api.usecase

import org.itis.project.sharedlogic.feature.main.api.model.IssPositionModel

interface GetIssPositionUseCase {
    suspend operator fun invoke(): IssPositionModel
}