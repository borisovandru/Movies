package com.borisov.movies.ui.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*

/**
 * @author Borisov Andrey on 28.06.2022
 **/
abstract class BaseViewModel : ViewModel() {
    val viewModelScopeCoroutine = CoroutineScope(
        Dispatchers.IO
                + SupervisorJob()
                + CoroutineExceptionHandler { _, throwable ->
            handleError(throwable)
        }
    )

    abstract fun handleError(throwable: Throwable)

    override fun onCleared() {
        super.onCleared()
        viewModelScopeCoroutine
            .coroutineContext
            .cancel()
    }
}