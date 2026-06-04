package org.itis.project.sharedlogic.feature

import org.itis.project.sharedlogic.feature.apod.impl.di.apodModule
import org.itis.project.sharedlogic.feature.main.impl.di.mainModule
import org.itis.project.sharedlogic.feature.planets.impl.di.planetsModule
import org.koin.dsl.module

val featureModule = module {
    includes (
        mainModule,
        apodModule,
        planetsModule
    )
}