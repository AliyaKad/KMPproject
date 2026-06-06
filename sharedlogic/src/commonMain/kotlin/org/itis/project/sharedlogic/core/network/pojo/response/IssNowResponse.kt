package org.itis.project.sharedlogic.core.network.pojo.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IssNowResponse(
    @SerialName("message")
    val message: String?,
    @SerialName("timestamp")
    val timestamp: Long?,
    @SerialName("iss_position")
    val issPosition: IssPositionResponse?
)

@Serializable
data class IssPositionResponse(
    @SerialName("latitude")
    val latitude: String?,
    @SerialName("longitude")
    val longitude: String?
)