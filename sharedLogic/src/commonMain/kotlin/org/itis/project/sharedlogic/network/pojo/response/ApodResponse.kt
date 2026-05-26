package org.itis.project.sharedlogic.network.pojo.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApodResponse(
    val date: String,
    val title: String,
    val explanation: String,
    val url: String,
    val hdurl: String? = null,
    @SerialName("media_type") val mediaType: String,
    val copyright: String? = null
)