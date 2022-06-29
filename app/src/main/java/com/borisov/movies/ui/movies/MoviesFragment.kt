package com.borisov.movies.ui.movies

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.borisov.movies.R
import com.borisov.movies.databinding.FragmentMoviesBinding
import com.borisov.movies.domain.AppState
import com.borisov.movies.domain.IAppState
import com.borisov.movies.domain.models.MoviesResponse
import com.borisov.movies.ui.ToolbarListener
import com.borisov.movies.ui.base.BaseFragment
import com.borisov.movies.ui.movies.adapter.MovieAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @author Borisov Andrey on 27.06.2022
 **/

class MoviesFragment : BaseFragment<FragmentMoviesBinding>(R.layout.fragment_movies),
    MovieAdapter.Delegate {

    private val viewModel: MoviesViewModel by viewModel()
    private val adapter by lazy { MovieAdapter(this) }

    override fun initListeners() {}

    override fun initObservers() {
        viewModel.getMoviesLiveData()
            .observe(viewLifecycleOwner) { res -> renderData(result = res) }
    }

    private fun showLoading(isShow: Boolean) {
        viewBinding.progress.isVisible = isShow
    }

    override fun renderData(result: IAppState) {
        when (result) {
            is AppState.Error -> showLoading(false)
            is AppState.Loading -> showLoading(true)
            is AppState.Success<*> -> renderSuccess(result)
        }
    }

    private fun renderSuccess(result: AppState.Success<*>) {
        showLoading(false)
        when (val moveResponse = result.data) {
            is MoviesResponse -> {
                adapter.setItems(moveResponse.movies)
                if (adapter.itemCount > ZERO_VALUE)
                    viewModel.setCurrentPage(moveResponse.page, moveResponse.totalPages)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerSetting()

        viewModel.getMoviesTopRated(true)
    }

    private fun initRecyclerSetting() {
        viewBinding.recyclerView.also { recycler ->
            recycler.adapter = adapter
            recycler.setHasFixedSize(true)
        }
    }

    override fun onStart() {
        showToolBar(false)
        super.onStart()
    }

    override fun onStop() {
        showToolBar(true)
        super.onStop()
    }

    private fun showToolBar(visible: Boolean) {
        (requireActivity() as ToolbarListener).showToolBar(visible)
    }

    override fun onItemClick(movie: MoviesResponse.Movie) {}

    override fun getMoreMovies() {
        viewModel.getMoviesTopRated(true)
    }

    companion object {
        private const val ZERO_VALUE = 0
    }
}