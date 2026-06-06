package org.itis.project.sharedlogic.feature.apod.impl.presentation.ApodSearch

import kotlinx.datetime.LocalDate

sealed class ApodSearchEvent {
    data class SelectDate(val date: LocalDate) : ApodSearchEvent()
    data object LoadApod : ApodSearchEvent()
    data object ResetError : ApodSearchEvent()
}