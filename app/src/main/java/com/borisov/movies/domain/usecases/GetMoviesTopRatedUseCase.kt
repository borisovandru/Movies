package com.borisov.movies.domain.usecases

import com.borisov.movies.domain.AppState
import com.borisov.movies.domain.models.MoviesResponse
import com.borisov.movies.domain.repository.MovieRepository

/**
 * @author Borisov Andrey on 28.06.2022
 **/
class GetMoviesTopRatedUseCase(private val repository: MovieRepository) {
    suspend fun execute(adult: Boolean = false, page: Int): AppState<MoviesResponse> =
        repository.getMoviesTopRated(adult, page)
}