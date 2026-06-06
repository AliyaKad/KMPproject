package org.itis.project.sharedlogic.feature.apod.impl.presentation.ApodDetail

sealed class ApodDetailEvent {
    data class Load(val date: String) : ApodDetailEvent()
    data object ToggleFavorite : ApodDetailEvent()
    data object Share : ApodDetailEvent()
}