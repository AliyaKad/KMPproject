package org.itis.project.sharedlogic.feature.main.impl.presentation

sealed class HomeEvent {
    data object Load : HomeEvent()
    data object Refresh : HomeEvent()
}