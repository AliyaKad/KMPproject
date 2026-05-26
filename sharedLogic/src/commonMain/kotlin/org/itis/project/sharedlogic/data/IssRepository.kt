package org.itis.project.sharedlogic.data

import org.itis.project.sharedlogic.domain.IssPosition
import org.itis.project.sharedlogic.network.api.IssApi
import org.itis.project.sharedlogic.network.mapper.toDomain

class IssRepository(private val api: IssApi) {
    suspend fun getIssPosition(): IssPosition = api.getIssNow().toDomain()
}