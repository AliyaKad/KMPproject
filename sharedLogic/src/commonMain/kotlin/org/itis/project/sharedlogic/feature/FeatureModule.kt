package org.itis.project.sharedlogic.feature

import org.itis.project.sharedlogic.feature.main.impl.di.mainModule
import org.koin.dsl.module

val featureModule = module {
    includes (
        mainModule
    )
}