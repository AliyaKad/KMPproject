package org.itis.project.sharedlogic.data

import org.itis.project.sharedlogic.domain.Apod
import org.itis.project.sharedlogic.network.api.NasaApi
import org.itis.project.sharedlogic.network.mapper.toDomain


class NasaRepository(private val api: NasaApi) {
    suspend fun getApod(): Apod = api.getApod().toDomain()
}