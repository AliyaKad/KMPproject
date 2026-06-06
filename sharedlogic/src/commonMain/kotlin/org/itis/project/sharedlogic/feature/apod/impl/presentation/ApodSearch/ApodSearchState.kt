package org.itis.project.sharedlogic.feature.apod.impl.presentation.ApodSearch

import org.itis.project.sharedlogic.feature.main.api.model.ApodModel
import kotlinx.datetime.LocalDate

data class ApodSearchState(
    val isLoading: Boolean = false,
    val selectedDate: LocalDate? = null,
    val apod: ApodModel? = null,
    val error: String? = null
)