package com.borisov.movies.di

import com.borisov.movies.ui.detail.DetailViewModel
import com.borisov.movies.ui.movies.MoviesViewModel
import com.borisov.movies.ui.settings.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * @author Borisov Andrey on 28.06.2022
 **/
object Di {
    fun viewModelModule() = module {

        viewModel() {
            MoviesViewModel()
        }

        viewModel() {
            DetailViewModel()
        }

        viewModel() {
            SettingsViewModel()
        }
    }
}