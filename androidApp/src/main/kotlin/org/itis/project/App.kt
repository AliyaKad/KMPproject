package org.itis.project

import android.app.Application
import com.google.firebase.crashlytics.FirebaseCrashlytics
import org.itis.project.sharedlogic.feature.auth.impl.di.createAppModule
import org.itis.project.sharedlogic.core.di.networkModule
import org.itis.project.sharedlogic.feature.featureModule
import org.itis.project.sharedlogic.feature.main.impl.di.mainModule
import org.itis.project.sharedui.di.uiModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        FirebaseCrashlytics.getInstance()

        val contextModule = module {
            single { applicationContext }
        }

        startKoin {
            androidContext(this@App)
            modules(
                contextModule,
                networkModule,
                featureModule,
                createAppModule(AndroidPlatformDependencies(this@App)),
                uiModule
            )
        }
    }
}