package com.borisov.movies.ui.movies

import androidx.lifecycle.MutableLiveData
import com.borisov.movies.domain.AppState
import com.borisov.movies.domain.models.MoviesResponse
import com.borisov.movies.domain.usecases.GetMoviesTopRatedUseCase
import com.borisov.movies.ui.base.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * @author Borisov Andrey on 27.06.2022
 **/

class MoviesViewModel(
    private val moviesLiveData: MutableLiveData<AppState<MoviesResponse>>
    = MutableLiveData<AppState<MoviesResponse>>(),
    private val getMoviesTopRatedUseCase: GetMoviesTopRatedUseCase,
) : BaseViewModel() {

    private val allMovies: ArrayList<MoviesResponse.Movie> = arrayListOf()

    private var currentPage: Int = ONE_VALUE

    fun setCurrentPage(value: Int, totalPage: Int) {
        if (value < totalPage) {
            currentPage = value + ONE_VALUE
        }
    }

    override fun handleError(throwable: Throwable) {}

    fun getMoviesLiveData() = moviesLiveData

    fun getMoviesTopRated(adult: Boolean = false, page: Int = currentPage): Job =
        viewModelScopeCoroutine.launch {
            getMoviesLiveData().postValue(AppState.Loading)
            val movies = getMoviesTopRatedUseCase.execute(adult, page)
            if (movies is AppState.Success) {
                when (val movies = movies.data) {
                    is MoviesResponse -> {
                        allMovies.addAll(movies.movies)
                        getMoviesLiveData().postValue(
                            AppState.Success<MoviesResponse>(movies.copy(movies = allMovies))
                        )
                    }
                }
            }
        }

    companion object {
        private const val ONE_VALUE = 1
    }
}