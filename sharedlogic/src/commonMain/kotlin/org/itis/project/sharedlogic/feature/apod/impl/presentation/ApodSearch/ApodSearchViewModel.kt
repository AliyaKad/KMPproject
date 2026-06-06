package org.itis.project.sharedlogic.feature.apod.impl.presentation.ApodSearch

import kotlinx.coroutines.launch
import org.itis.project.sharedlogic.core.viewmodel.BaseViewModel
import kotlinx.datetime.LocalDate
import org.itis.project.sharedlogic.feature.apod.api.usecase.GetApodByDateUseCase
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class ApodSearchViewModel : BaseViewModel<ApodSearchState, ApodSearchEvent, Nothing>(
    initialState = ApodSearchState()
), KoinComponent {

    private val getApodByDateUseCase: GetApodByDateUseCase by inject()

    override fun obtainIntent(intent: ApodSearchEvent) {
        when (intent) {
            is ApodSearchEvent.SelectDate -> selectDate(intent.date)
            ApodSearchEvent.LoadApod -> loadApod()
            ApodSearchEvent.ResetError -> resetError()
        }
    }

    private fun selectDate(date: LocalDate) {
        updateState { it.copy(selectedDate = date, error = null, apod = null) }
        loadApod()
    }

    private fun loadApod() {
        val date = currentState().selectedDate
        val dateString = date.toString()

        updateState { it.copy(isLoading = true, error = null) }

        viewModelScope.launch {
            runCatching { getApodByDateUseCase(dateString) }
                .onSuccess { apod ->
                    updateState { it.copy(isLoading = false, apod = apod, error = null) }
                }
                .onFailure { e ->
                    updateState {
                        it.copy(
                            isLoading = false,
                            apod = null,
                            error = e.message ?: "Не удалось загрузить картинку за выбранную дату"
                        )
                    }
                }
        }
    }

    private fun resetError() {
        updateState { it.copy(error = null) }
    }
}