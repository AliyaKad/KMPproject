package org.itis.project.di

import org.koin.dsl.module
import org.itis.project.sharedlogic.data.repository.auth.AuthRepository
import org.itis.project.sharedlogic.feature.auth.impl.data.UserDao
import org.itis.project.sharedlogic.feature.auth.impl.data.UserPreferences
import org.itis.project.domain.CheckAuthUseCase
import org.itis.project.domain.LoginUseCase
import org.itis.project.domain.LogoutUseCase
import org.itis.project.domain.RegisterUseCase
import org.itis.project.presentation.AuthViewModel
import org.itis.project.auth.cache.UserDatabase
import org.itis.project.sharedlogic.di.PlatformDependencies

fun createAppModule(dependencies: PlatformDependencies) = module {
    single { UserDatabase(dependencies.createDriver()) }
    single { UserDao(get()) }

    single<UserPreferences> { dependencies.createUserPreferences() }
    single { AuthRepository(get(), get()) }

    factory { LoginUseCase(get()) }
    factory { RegisterUseCase(get()) }
    factory { CheckAuthUseCase(get()) }
    factory { LogoutUseCase(get()) }

    factory { AuthViewModel(get(), get(), get(), get()) }
}