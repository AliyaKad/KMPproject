package org.itis.project.sharedlogic.feature.main.impl.repository

import org.itis.project.sharedlogic.core.network.api.IssApi
import org.itis.project.sharedlogic.core.network.mapper.mapToEntity
import org.itis.project.sharedlogic.feature.main.api.model.IssPositionModel
import org.itis.project.sharedlogic.feature.main.api.repository.IssRepository

internal class IssRepositoryImpl(
    private val issApi: IssApi
): IssRepository {

    override suspend fun now(): IssPositionModel {
        return issApi.getIssNow().mapToEntity()
    }
}