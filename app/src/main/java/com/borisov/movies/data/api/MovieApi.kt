package com.borisov.movies.data.api

import com.borisov.movies.BuildConfig
import com.borisov.movies.domain.models.ActorsResponse
import com.borisov.movies.domain.models.MovieResponse
import com.borisov.movies.domain.models.MoviesResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * @author Borisov Andrey on 28.06.2022
 **/
interface MovieApi {

    @GET("top_rated?api_key=${BuildConfig.MOVIE_API_KEY}&language=ru-RU")
    fun getMoviesTopRatedAsync(
        @Query("include_adult") adult: Boolean,
        @Query("page") page: Int,
    ): Deferred<MoviesResponse>

    @GET("{movie_id}?api_key=${BuildConfig.MOVIE_API_KEY}&language=ru-RU")
    fun getMovieDetailByIdAsync(
        @Path("movie_id") movieId: Int,
    ): Deferred<MovieResponse>

    @GET("{movie_id}/casts?api_key=${BuildConfig.MOVIE_API_KEY}&language=ru-RU")
    fun getActorsListAsync(
        @Path("movie_id") movieId: Int,
    ): Deferred<ActorsResponse>
}