package org.itis.project.sharedlogic.core.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class FavoriteApod(
    val date: String,
    val title: String,
    val explanation: String,
    val url: String,
    val hdurl: String?,
    val mediaType: String,
    val copyright: String?,
    val addedAt: Long = System.currentTimeMillis()
)