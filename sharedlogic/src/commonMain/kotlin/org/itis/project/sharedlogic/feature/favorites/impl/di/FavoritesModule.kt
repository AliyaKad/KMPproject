package org.itis.project.sharedlogic.feature.favorites.impl.di

import org.itis.project.Database
import org.koin.core.module.Module
import org.koin.dsl.module
import org.itis.project.sharedlogic.feature.favorites.api.data.FavoritesDao
import org.itis.project.sharedlogic.feature.favorites.api.data.FavoritesRepository
import org.itis.project.sharedlogic.feature.favorites.api.domain.*
import org.itis.project.sharedlogic.feature.favorites.impl.data.FavoritesRepositoryImpl
import org.itis.project.sharedlogic.feature.favorites.impl.domain.*
import org.itis.project.sharedlogic.feature.favorites.impl.presentation.FavoritesViewModel

val favoritesModule: Module = module {
    single { FavoritesDao(get<Database>()) }

    single<FavoritesRepository> { FavoritesRepositoryImpl(get()) }

    single<AddToFavoritesUseCase> { AddToFavoritesUseCaseImpl(get()) }
    single<RemoveFromFavoritesUseCase> { RemoveFromFavoritesUseCaseImpl(get()) }
    single<GetFavoritesUseCase> { GetFavoritesUseCaseImpl(get()) }
    single<IsFavoriteUseCase> { IsFavoriteUseCaseImpl(get()) }

    factory { FavoritesViewModel(get(), get()) }
}