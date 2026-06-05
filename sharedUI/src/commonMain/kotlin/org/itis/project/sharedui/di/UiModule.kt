package org.itis.project.sharedui.di

import org.itis.project.sharedlogic.feature.auth.impl.presentation.AuthViewModel
import org.itis.project.sharedlogic.feature.profile.impl.presentation.ProfileViewModel
import org.koin.dsl.module

val uiModule = module {
    factory { AuthViewModel(get(), get(), get(), get()) }
    factory { ProfileViewModel(get(), get(), get()) }
}

