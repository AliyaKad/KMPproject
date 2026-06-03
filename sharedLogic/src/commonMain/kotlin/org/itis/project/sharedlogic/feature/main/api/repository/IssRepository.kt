package org.itis.project.sharedlogic.feature.main.api.repository

import org.itis.project.sharedlogic.feature.main.api.model.IssPosition

interface IssRepository {
    suspend fun now(): IssPosition
}