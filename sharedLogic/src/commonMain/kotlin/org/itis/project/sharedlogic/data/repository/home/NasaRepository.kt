package org.itis.project.sharedlogic.data.repository.home

import org.itis.project.sharedlogic.domain.model.Apod
import org.itis.project.sharedlogic.network.api.NasaApi

class NasaRepository(private val api: NasaApi) {

    suspend fun apod(date: String? = null): Apod = api.getApod(date).let {
        Apod(
            date = it.date,
            title = it.title,
            explanation = it.explanation,
            url = it.url,
            hdurl = it.hdurl,
            mediaType = it.mediaType,
            copyright = it.copyright
        )
    }
}
