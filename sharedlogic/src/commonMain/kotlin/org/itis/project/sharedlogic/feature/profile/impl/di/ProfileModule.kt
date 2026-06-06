package org.itis.project.sharedlogic.feature.profile.impl.di

import org.itis.project.sharedlogic.feature.profile.api.domain.GetCurrentUserUseCase
import org.itis.project.sharedlogic.feature.profile.api.domain.GetThemeUseCase
import org.itis.project.sharedlogic.feature.profile.api.domain.UpdateThemeUseCase
import org.koin.core.module.Module
import org.koin.dsl.module
import org.itis.project.sharedlogic.feature.profile.impl.data.ThemeManager
import org.itis.project.sharedlogic.feature.profile.impl.domain.GetCurrentUserUseCaseImpl
import org.itis.project.sharedlogic.feature.profile.impl.domain.GetThemeUseCaseImpl
import org.itis.project.sharedlogic.feature.profile.impl.domain.UpdateThemeUseCaseImpl
import org.itis.project.sharedlogic.feature.profile.impl.presentation.ProfileViewModel


val profileModule: Module = module {

    single { ThemeManager(get()) }

    factory { ProfileViewModel(get(), get()) }

    single<GetCurrentUserUseCase> { GetCurrentUserUseCaseImpl(get()) }
    single<GetThemeUseCase> { GetThemeUseCaseImpl(get()) }
    single<UpdateThemeUseCase> { UpdateThemeUseCaseImpl(get()) }
}