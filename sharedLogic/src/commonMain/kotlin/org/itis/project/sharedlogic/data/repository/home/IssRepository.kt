package org.itis.project.sharedlogic.data.repository.home

import org.itis.project.sharedlogic.domain.model.IssPosition
import org.itis.project.sharedlogic.network.api.IssApi

class IssRepository(private val api: IssApi) {

    suspend fun now(): IssPosition = api.getIssNow().let {
        IssPosition(
            latitude = it.issPosition.latitude.toDouble(),
            longitude = it.issPosition.longitude.toDouble(),
            timestampSeconds = it.timestamp
        )
    }
}
