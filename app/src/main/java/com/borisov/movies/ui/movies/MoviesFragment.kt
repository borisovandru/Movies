package com.borisov.movies.ui.movies

import com.borisov.movies.R
import com.borisov.movies.databinding.FragmentMoviesBinding
import com.borisov.movies.domain.IAppState
import com.borisov.movies.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @author Borisov Andrey on 27.06.2022
 **/

class MoviesFragment : BaseFragment<FragmentMoviesBinding>(R.layout.fragment_movies) {

    val viewModel: MoviesViewModel by viewModel()

    override fun initListeners() {}

    override fun initObservers() {}

    override fun renderData(result: IAppState) {}
}