package com.borisov.movies.domain.usecases

import com.borisov.movies.domain.AppState
import com.borisov.movies.domain.models.MoviesResponse
import com.borisov.movies.domain.repository.MovieRepository

/**
 * @author Borisov Andrey on 30.06.2022
 **/
class SearchMoviesUseCase(private val repository: MovieRepository) {
    suspend fun execute(query: String): AppState<MoviesResponse> =
        repository.searchMovie(query)
}