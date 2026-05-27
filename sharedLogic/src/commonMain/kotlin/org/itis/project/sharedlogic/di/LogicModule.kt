package org.itis.project.sharedlogic.di

import org.itis.project.sharedlogic.data.repository.home.IssRepository
import org.itis.project.sharedlogic.data.repository.home.NasaRepository
import org.itis.project.sharedlogic.network.api.IssApi
import org.itis.project.sharedlogic.network.api.NasaApi
import org.itis.project.sharedlogic.network.createHttpClient
import org.itis.project.sharedlogic.presentation.home.HomeViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val logicModule = module {
    single { createHttpClient() }
    singleOf(::NasaApi)
    singleOf(::IssApi)
    singleOf(::NasaRepository)
    singleOf(::IssRepository)
    factoryOf(::HomeViewModel)
}