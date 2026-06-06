package org.itis.project.sharedlogic.feature.favorites.impl.data

import org.itis.project.sharedlogic.core.domain.model.FavoriteApod
import org.itis.project.sharedlogic.feature.favorites.api.data.FavoritesDao
import org.itis.project.sharedlogic.feature.favorites.api.data.FavoritesRepository

class FavoritesRepositoryImpl(
    private val favoritesDao: FavoritesDao
) : FavoritesRepository {

    override suspend fun addToFavorites(apod: FavoriteApod) {
        favoritesDao.insert(apod)
    }

    override suspend fun removeFromFavorites(date: String) {
        favoritesDao.delete(date)
    }

    override suspend fun getAllFavorites(): List<FavoriteApod> {
        return favoritesDao.getAll()
    }

    override suspend fun isFavorite(date: String): Boolean {
        return favoritesDao.isFavorite(date)
    }

    override suspend fun getFavoritesCount(): Long {
        return favoritesDao.getCount()
    }
}