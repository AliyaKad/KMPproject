package org.itis.project.sharedlogic.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Apod(
    val date: String,
    val title: String,
    val explanation: String,
    val url: String,
    val hdurl: String?,
    val mediaType: String,
    val copyright: String?
)

data class IssPosition(
    val latitude: Double,
    val longitude: Double,
    val timestampSeconds: Long
)

sealed class Resource<out T> {
    data object Loading : Resource<Nothing>()
    data class Success<T>(val data: T) : Resource<T>()
    data class Error(val message: String, val throwable: Throwable? = null) : Resource<Nothing>()
}