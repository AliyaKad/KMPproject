package org.itis.project.sharedlogic.feature.main.impl.presentation

import org.itis.project.sharedlogic.feature.main.api.model.ApodModel
import org.itis.project.sharedlogic.feature.main.api.model.IssPositionModel

data class HomeState(
    val greeting: String = "",
    val apod: ApodModel? = null,
    val apodLoading: Boolean = true,
    val apodError: String? = null,
    val planetName: String = "",
    val planetFact: String = "",
    val issPosition: IssPositionModel? = null,
    val issLoading: Boolean = true,
    val issError: String? = null
)
