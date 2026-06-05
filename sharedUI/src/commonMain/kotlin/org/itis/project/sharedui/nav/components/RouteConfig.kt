package org.itis.project.sharedui.nav.components

import androidx.navigation3.runtime.NavKey
import androidx.savedstate.serialization.SavedStateConfiguration
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import org.itis.project.sharedui.nav.*


val RouteConfig = SavedStateConfiguration {
    serializersModule = SerializersModule {
        polymorphic(NavKey::class) {
            subclass(HomeRoute::class, HomeRoute.serializer())
            subclass(ApodRoute::class, ApodRoute.serializer())
            subclass(PlanetsRoute::class, PlanetsRoute.serializer())
            subclass(IssRoute::class, IssRoute.serializer())
            subclass(FavoritesRoute::class, FavoritesRoute.serializer())
            subclass(ProfileRoute::class, ProfileRoute.serializer())
            subclass(PlanetDetailRoute::class, PlanetDetailRoute.serializer())
        }
    }
}