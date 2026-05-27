package org.itis.project.sharedlogic.network.mapper

import org.itis.project.sharedlogic.domain.model.Apod
import org.itis.project.sharedlogic.network.pojo.response.ApodResponse

fun ApodResponse.toDomain(): Apod = Apod(
    date = date,
    title = title,
    explanation = explanation,
    url = url,
    hdurl = hdurl,
    mediaType = mediaType,
    copyright = copyright
)