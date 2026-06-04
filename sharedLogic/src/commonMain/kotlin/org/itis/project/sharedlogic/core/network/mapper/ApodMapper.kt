package org.itis.project.sharedlogic.core.network.mapper

import org.itis.project.sharedlogic.feature.main.api.model.ApodModel
import org.itis.project.sharedlogic.core.network.pojo.response.ApodResponse

fun ApodResponse.mapToEntity(): ApodModel = ApodModel(
    date = date ?: "",
    title = title ?: "",
    explanation = explanation ?: "",
    url = url ?: "",
    hdurl = hdurl,
    mediaType = mediaType ?: "",
    copyright = copyright
)