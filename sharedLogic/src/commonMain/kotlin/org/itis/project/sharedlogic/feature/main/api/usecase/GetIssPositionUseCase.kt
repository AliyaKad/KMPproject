package org.itis.project.sharedlogic.feature.main.api.usecase

import org.itis.project.sharedlogic.feature.main.api.model.IssPosition

interface GetIssPositionUseCase {
    suspend operator fun invoke(): IssPosition
}