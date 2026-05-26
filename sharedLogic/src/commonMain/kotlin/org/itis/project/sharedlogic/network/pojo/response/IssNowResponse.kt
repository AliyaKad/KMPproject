package org.itis.project.sharedlogic.network.pojo.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IssNowResponse(
    val message: String,
    val timestamp: Long,
    @SerialName("iss_position") val issPosition: IssPositionResponse
)

@Serializable
data class IssPositionResponse(
    val latitude: String,
    val longitude: String
)