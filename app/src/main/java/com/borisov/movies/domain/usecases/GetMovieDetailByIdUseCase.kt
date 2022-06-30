package com.borisov.movies.domain.usecases

import com.borisov.movies.domain.AppState
import com.borisov.movies.domain.models.MovieResponse
import com.borisov.movies.domain.repository.MovieRepository

/**
 * @author Borisov Andrey on 29.06.2022
 **/
class GetMovieDetailByIdUseCase(private val repository: MovieRepository) {
    suspend fun execute(movieId: Int): AppState<MovieResponse> =
        repository.getMovieDetailById(movieId)
}