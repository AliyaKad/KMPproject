package org.itis.project.sharedlogic.feature.main.impl.presentation

import org.itis.project.sharedlogic.feature.main.api.model.Apod
import org.itis.project.sharedlogic.feature.main.api.model.IssPosition

data class HomeState(
    val greeting: String = "",
    val apod: Apod? = null,
    val apodLoading: Boolean = true,
    val apodError: String? = null,
    val planetName: String = "",
    val planetFact: String = "",
    val issPosition: IssPosition? = null,
    val issLoading: Boolean = true,
    val issError: String? = null
)
