package org.itis.project.sharedlogic.feature.main.api.repository

import org.itis.project.sharedlogic.feature.main.api.model.ApodModel

interface NasaRepository {
    suspend fun apod(date: String? = null): ApodModel
}