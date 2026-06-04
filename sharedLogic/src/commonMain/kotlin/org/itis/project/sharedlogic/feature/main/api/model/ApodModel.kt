package org.itis.project.sharedlogic.feature.main.api.model

import kotlinx.serialization.Serializable

@Serializable
data class ApodModel(
    val date: String,
    val title: String,
    val explanation: String,
    val url: String,
    val hdurl: String?,
    val mediaType: String,
    val copyright: String?
)