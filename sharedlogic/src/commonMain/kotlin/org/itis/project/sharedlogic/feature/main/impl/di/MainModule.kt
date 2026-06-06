package org.itis.project.sharedlogic.feature.main.impl.di

import org.itis.project.sharedlogic.core.network.api.IssApi
import org.itis.project.sharedlogic.core.network.api.NasaApi
import org.itis.project.sharedlogic.domain.usecase.GetApodUseCaseImpl
import org.itis.project.sharedlogic.feature.main.api.repository.IssRepository
import org.itis.project.sharedlogic.feature.main.api.repository.NasaRepository
import org.itis.project.sharedlogic.feature.main.api.usecase.GetApodUseCase
import org.itis.project.sharedlogic.feature.main.api.usecase.GetIssPositionUseCase
import org.itis.project.sharedlogic.feature.main.impl.repository.IssRepositoryImpl
import org.itis.project.sharedlogic.feature.main.impl.repository.NasaRepositoryImpl
import org.itis.project.sharedlogic.feature.main.impl.usecase.GetIssPositionUseCaseImpl
import org.itis.project.sharedlogic.feature.main.api.usecase.GetGreetingUseCase
import org.itis.project.sharedlogic.feature.planets.api.usecase.GetPlanetOfDayUseCase
import org.itis.project.sharedlogic.feature.main.impl.usecase.GetGreetingUseCaseImpl
import org.itis.project.sharedlogic.feature.planets.impl.usecase.GetPlanetOfDayUseCaseImpl
import org.itis.project.sharedlogic.feature.main.impl.presentation.HomeViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val mainModule = module {
    singleOf(::NasaApi)
    singleOf(::IssApi)

    single<NasaRepository> { NasaRepositoryImpl(get()) }
    single<IssRepository> { IssRepositoryImpl(get()) }

    factory<GetApodUseCase> { GetApodUseCaseImpl(get()) }
    factory<GetIssPositionUseCase> { GetIssPositionUseCaseImpl(get()) }
    factory<GetPlanetOfDayUseCase> { GetPlanetOfDayUseCaseImpl() }
    factory<GetGreetingUseCase> { GetGreetingUseCaseImpl() }

    factoryOf(::HomeViewModel)
}