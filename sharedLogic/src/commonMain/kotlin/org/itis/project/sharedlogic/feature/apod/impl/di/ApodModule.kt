package org.itis.project.sharedlogic.feature.apod.impl.di

import org.itis.project.sharedlogic.feature.apod.api.usecase.GetApodByDateUseCase
import org.itis.project.sharedlogic.feature.apod.impl.usecase.GetApodByDateUseCaseImpl
import org.itis.project.sharedlogic.feature.main.impl.presentation.ApodDetailViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val apodModule = module {
    factory<GetApodByDateUseCase> { GetApodByDateUseCaseImpl(get()) }

    factoryOf(::ApodDetailViewModel)
}