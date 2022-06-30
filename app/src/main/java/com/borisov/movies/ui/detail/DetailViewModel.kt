package com.borisov.movies.ui.detail

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import com.borisov.movies.domain.AppState
import com.borisov.movies.domain.models.ActorsResponse
import com.borisov.movies.domain.models.MovieResponse
import com.borisov.movies.domain.usecases.GetActorsUseCase
import com.borisov.movies.domain.usecases.GetMovieDetailByIdUseCase
import com.borisov.movies.ui.base.BaseViewModel

/**
 * @author Borisov Andrey on 27.06.2022
 **/

class DetailViewModel(
    private val movieLiveData: MutableLiveData<AppState<MovieResponse>> = MutableLiveData<AppState<MovieResponse>>(),
    private val actorLiveData: MutableLiveData<AppState<ActorsResponse>> = MutableLiveData<AppState<ActorsResponse>>(),
    private val getMovieDetailByIdUseCase: GetMovieDetailByIdUseCase,
    private val getActorsUseCase: GetActorsUseCase,
) : BaseViewModel() {

    override fun handleError(throwable: Throwable) {}

    fun getMovieLiveData() = movieLiveData
    fun getActorLiveData() = actorLiveData

    fun getMovieDetail(movieId: Int): Job =
        viewModelScopeCoroutine.launch {
            getMovieLiveData().postValue(AppState.Loading)
            getMovieLiveData().postValue(getMovieDetailByIdUseCase.execute(movieId))
        }

    fun getActors(movieId: Int): Job =
        viewModelScopeCoroutine.launch {
            getActorLiveData().postValue(AppState.Loading)
            getActorLiveData().postValue(getActorsUseCase.execute(movieId))
        }
}