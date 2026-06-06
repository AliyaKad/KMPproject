package org.itis.project.sharedlogic.core.network.pojo.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApodResponse(
    @SerialName("date")
    val date: String?,
    @SerialName("title")
    val title: String?,
    @SerialName("explanation")
    val explanation: String?,
    @SerialName("url")
    val url: String?,
    @SerialName("hd_url")
    val hdurl: String? = null,
    @SerialName("media_type")
    val mediaType: String?,
    @SerialName("copyright")
    val copyright: String? = null
)