package org.itis.project

import android.app.Application
import org.itis.project.sharedlogic.di.logicModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class SpaceVueApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@SpaceVueApp)
            modules(logicModule)
        }
    }
}