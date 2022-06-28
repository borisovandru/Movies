package com.borisov.movies.domain

/**
 * @author Borisov Andrey on 28.06.2022
 **/
sealed class AppState<out T : Any> : IAppState {
    data class Success<out T : Any>(val data: T) : AppState<T>()
    data class Error(val error: Throwable) : AppState<Nothing>()
    object Loading : AppState<Nothing>()
}

