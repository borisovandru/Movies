package com.borisov.movies.data.repository.datasource

import com.borisov.movies.data.api.MovieApi
import com.borisov.movies.domain.AppState
import com.borisov.movies.domain.models.MoviesResponse

/**
 * @author Borisov Andrey on 28.06.2022
 **/
class RemoteDataSourceImpl(private val movieApi: MovieApi) : RemoteDataSource {
    override suspend fun getMoviesTopRated(adult: Boolean, page: Int): AppState<MoviesResponse> =
        try {
            val result = movieApi.getMoviesTopRatedAsync(adult, page).await()

            if (result.movies.isNotEmpty()) {
                AppState.Success(result)
            } else {
                AppState.Error(Exception(NO_DATA))
            }
        } catch (err: Exception) {
            AppState.Error(err)
        }

    companion object {
        const val NO_DATA = "Отсутствуют данные"
    }
}