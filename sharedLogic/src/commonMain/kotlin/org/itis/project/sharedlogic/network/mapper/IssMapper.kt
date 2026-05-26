package org.itis.project.sharedlogic.network.mapper

import org.itis.project.sharedlogic.domain.IssPosition
import org.itis.project.sharedlogic.network.pojo.response.IssNowResponse

fun IssNowResponse.toDomain(): IssPosition = IssPosition(
    latitude = issPosition.latitude.toDouble(),
    longitude = issPosition.longitude.toDouble(),
    timestampSeconds = timestamp
)