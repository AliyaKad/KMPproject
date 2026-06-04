package org.itis.project.sharedlogic.feature.planets.impl.di

import org.itis.project.sharedlogic.core.network.api.SolarApi
import org.itis.project.sharedlogic.feature.planets.api.repository.PlanetsRepository
import org.itis.project.sharedlogic.feature.planets.api.usecase.GetPlanetDetailUseCase
import org.itis.project.sharedlogic.feature.planets.api.usecase.GetPlanetsUseCase
import org.itis.project.sharedlogic.feature.planets.impl.presentation.PlanetDetailViewModel
import org.itis.project.sharedlogic.feature.planets.impl.presentation.PlanetsViewModel
import org.itis.project.sharedlogic.feature.planets.impl.repository.PlanetsRepositoryImpl
import org.itis.project.sharedlogic.feature.planets.impl.usecase.GetPlanetDetailUseCaseImpl
import org.itis.project.sharedlogic.feature.planets.impl.usecase.GetPlanetsUseCaseImpl
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val planetsModule = module {
    singleOf(::SolarApi)
    single<PlanetsRepository> { PlanetsRepositoryImpl(get()) }
    factory<GetPlanetsUseCase> { GetPlanetsUseCaseImpl(get()) }
    factory<GetPlanetDetailUseCase> { GetPlanetDetailUseCaseImpl(get()) }
    factoryOf(::PlanetsViewModel)
    factoryOf(::PlanetDetailViewModel)
}