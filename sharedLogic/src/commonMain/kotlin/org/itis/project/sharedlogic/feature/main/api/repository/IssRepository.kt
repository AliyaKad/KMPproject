package org.itis.project.sharedlogic.feature.main.api.repository

import org.itis.project.sharedlogic.feature.main.api.model.IssPositionModel

interface IssRepository {
    suspend fun now(): IssPositionModel
}