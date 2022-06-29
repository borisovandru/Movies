package com.borisov.movies.data.api

import com.borisov.movies.BuildConfig
import com.borisov.movies.domain.models.MoviesResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author Borisov Andrey on 28.06.2022
 **/
interface MovieApi {

    @GET("top_rated?api_key=${BuildConfig.MOVIE_API_KEY}&language=ru-RU")
    fun getMoviesTopRatedAsync(
        @Query("include_adult") adult: Boolean,
        @Query("page") page: Int
    ): Deferred<MoviesResponse>
}