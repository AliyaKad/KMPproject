package org.itis.project.sharedlogic.feature.main.impl.repository

import org.itis.project.sharedlogic.core.network.api.NasaApi
import org.itis.project.sharedlogic.core.network.mapper.mapToEntity
import org.itis.project.sharedlogic.feature.main.api.model.ApodModel
import org.itis.project.sharedlogic.feature.main.api.repository.NasaRepository

internal class NasaRepositoryImpl(
    private val nasaApi: NasaApi
): NasaRepository {

    override suspend fun apod(date: String?): ApodModel {
        return nasaApi.getApod(date = date).mapToEntity()
    }
}