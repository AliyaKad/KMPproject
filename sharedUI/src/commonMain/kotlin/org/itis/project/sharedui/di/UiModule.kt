package org.itis.project.sharedui.di

import org.itis.project.sharedui.features.auth.AuthViewModel
import org.itis.project.sharedui.features.profile.ProfileViewModel
import org.koin.dsl.module

val uiModule = module {
    factory { AuthViewModel(get(), get(), get(), get()) }
    factory { ProfileViewModel(get(), get(), get()) }
}

