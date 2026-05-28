package org.itis.project

import android.app.Application
import org.itis.project.sharedlogic.feature.auth.impl.di.createAppModule
import org.itis.project.sharedlogic.di.logicModule
import org.itis.project.sharedui.di.uiModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class SpaceVueApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@SpaceVueApp)
            modules(
                logicModule,
                createAppModule(AndroidPlatformDependencies(this@SpaceVueApp)),
                uiModule
            )
        }
    }
}