package com.borisov.movies.data.repository

import com.borisov.movies.data.repository.datasource.RemoteDataSource
import com.borisov.movies.domain.AppState
import com.borisov.movies.domain.models.ActorsResponse
import com.borisov.movies.domain.models.MovieResponse
import com.borisov.movies.domain.models.MoviesResponse
import com.borisov.movies.domain.repository.MovieRepository

/**
 * @author Borisov Andrey on 28.06.2022
 **/
class MovieRepositoryImpl(private val dataSource: RemoteDataSource) : MovieRepository {
    override suspend fun getMoviesTopRated(adult: Boolean, page: Int): AppState<MoviesResponse> =
        dataSource.getMoviesTopRated(adult, page)

    override suspend fun getMovieDetailById(movieId: Int): AppState<MovieResponse> =
        dataSource.getMovieDetailById(movieId)

    override suspend fun getActorsList(movieId: Int): AppState<ActorsResponse> =
        dataSource.getActorsList(movieId)

    override suspend fun searchMovie(query: String): AppState<MoviesResponse> =
        dataSource.searchMovie(query)
}