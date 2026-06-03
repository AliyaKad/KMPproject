package org.itis.project.sharedlogic.feature.main.impl.usecase

import org.itis.project.sharedlogic.feature.main.api.model.IssPosition
import org.itis.project.sharedlogic.feature.main.api.repository.IssRepository
import org.itis.project.sharedlogic.feature.main.api.usecase.GetIssPositionUseCase

internal class GetIssPositionUseCaseImpl(
    private val repository: IssRepository
): GetIssPositionUseCase {

    override suspend fun invoke(): IssPosition {
        return repository.now()
    }
}