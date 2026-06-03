package org.itis.project.sharedlogic.feature.main.api.repository

import org.itis.project.sharedlogic.feature.main.api.model.Apod

interface NasaRepository {
    suspend fun apod(): Apod
}