package org.itis.project.sharedlogic.feature.favorites.api.data

import org.itis.project.Database
import org.itis.project.sharedlogic.core.domain.model.FavoriteApod

class FavoritesDao(private val db: Database) {

    suspend fun insert(favorite: FavoriteApod) {
        db.databaseQueries.insertFavoriteApod(
            date = favorite.date,
            title = favorite.title,
            explanation = favorite.explanation,
            url = favorite.url,
            hdurl = favorite.hdurl,
            media_type = favorite.mediaType,
            copyright = favorite.copyright,
            added_at = favorite.addedAt
        )
    }

    suspend fun delete(date: String) {
        db.databaseQueries.deleteFavoriteApod(date)
    }

    suspend fun getAll(): List<FavoriteApod> {
        return db.databaseQueries.selectAllFavorites()
            .executeAsList()
            .map { row ->
                FavoriteApod(
                    date = row.date,
                    title = row.title,
                    explanation = row.explanation,
                    url = row.url,
                    hdurl = row.hdurl,
                    mediaType = row.media_type,
                    copyright = row.copyright,
                    addedAt = row.added_at
                )
            }
    }

    suspend fun isFavorite(date: String): Boolean {
        return db.databaseQueries.selectFavoriteByDate(date)
            .executeAsOneOrNull() != null
    }

    suspend fun getCount(): Long {
        return db.databaseQueries.countFavorites()
            .executeAsOne()
    }
}