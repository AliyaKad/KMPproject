package org.itis.project.sharedlogic.feature.profile.impl.di

import org.itis.project.sharedlogic.feature.profile.api.domain.GetCurrentUserUseCase
import org.itis.project.sharedlogic.feature.profile.api.domain.GetThemeUseCase
import org.itis.project.sharedlogic.feature.profile.api.domain.UpdateThemeUseCase
import org.koin.core.module.Module
import org.koin.dsl.module
import org.itis.project.sharedlogic.feature.profile.impl.domain.GetCurrentUserUseCaseImpl
import org.itis.project.sharedlogic.feature.profile.impl.domain.GetThemeUseCaseImpl
import org.itis.project.sharedlogic.feature.profile.impl.domain.UpdateThemeUseCaseImpl



val profileModule: Module = module {

    single<GetCurrentUserUseCase> { GetCurrentUserUseCaseImpl(get()) }
    single<GetThemeUseCase> { GetThemeUseCaseImpl(get()) }
    single<UpdateThemeUseCase> { UpdateThemeUseCaseImpl(get()) }
}