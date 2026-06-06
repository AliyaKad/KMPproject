package org.itis.project.sharedlogic.core.network.mapper

import org.itis.project.sharedlogic.feature.main.api.model.IssPositionModel
import org.itis.project.sharedlogic.core.network.pojo.response.IssNowResponse

fun IssNowResponse.mapToEntity(): IssPositionModel = IssPositionModel(
    latitude = issPosition?.latitude?.toDoubleOrNull() ?: 0.0,
    longitude = issPosition?.longitude?.toDoubleOrNull() ?: 0.0,
    timestampSeconds = timestamp ?: 0L
)