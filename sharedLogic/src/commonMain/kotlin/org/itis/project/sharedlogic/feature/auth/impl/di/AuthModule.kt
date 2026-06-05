package org.itis.project.sharedlogic.feature.auth.impl.di

import org.itis.project.Database
import org.itis.project.sharedlogic.feature.auth.impl.domain.CheckAuthUseCaseImpl
import org.itis.project.sharedlogic.feature.auth.impl.domain.LoginUseCaseImpl
import org.itis.project.sharedlogic.feature.auth.impl.domain.LogoutUseCaseImpl
import org.itis.project.sharedlogic.feature.auth.impl.domain.RegisterUseCaseImpl
import org.itis.project.sharedlogic.feature.auth.impl.data.AuthRepository
import org.itis.project.sharedlogic.core.di.PlatformDependencies
import org.itis.project.sharedlogic.feature.auth.api.domain.CheckAuthUseCase
import org.itis.project.sharedlogic.feature.auth.api.domain.LoginUseCase
import org.itis.project.sharedlogic.feature.auth.api.domain.LogoutUseCase
import org.itis.project.sharedlogic.feature.auth.api.domain.RegisterUseCase
import org.itis.project.sharedlogic.feature.auth.impl.data.UserDao
import org.itis.project.sharedlogic.feature.auth.impl.data.UserPreferences
import org.koin.dsl.module

fun createAppModule(dependencies: PlatformDependencies) = module {
    single { Database(dependencies.createDriver()) }
    single { UserDao(get()) }

    single<UserPreferences> { dependencies.createUserPreferences() }
    single { AuthRepository(get(), get()) }

    single<LoginUseCase> { LoginUseCaseImpl(get()) }
    single<RegisterUseCase> { RegisterUseCaseImpl(get()) }
    single<CheckAuthUseCase> { CheckAuthUseCaseImpl(get()) }
    single<LogoutUseCase> { LogoutUseCaseImpl(get()) }
}