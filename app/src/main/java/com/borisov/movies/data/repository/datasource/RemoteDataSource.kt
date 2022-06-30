package com.borisov.movies.data.repository.datasource

import com.borisov.movies.domain.AppState
import com.borisov.movies.domain.models.ActorsResponse
import com.borisov.movies.domain.models.MovieResponse
import com.borisov.movies.domain.models.MoviesResponse

/**
 * @author Borisov Andrey on 28.06.2022
 **/
interface RemoteDataSource {
    suspend fun getMoviesTopRated(adult: Boolean, page: Int): AppState<MoviesResponse>
    suspend fun getMovieDetailById(movieId: Int): AppState<MovieResponse>
    suspend fun getActorsList(movieId: Int): AppState<ActorsResponse>
}