package com.borisov.movies.ui.movies

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import com.borisov.movies.R
import com.borisov.movies.databinding.FragmentMoviesBinding
import com.borisov.movies.domain.AppState
import com.borisov.movies.domain.models.MoviesResponse
import com.borisov.movies.ui.base.BaseFragment
import com.borisov.movies.ui.detail.DetailFragment.Companion.KEY_MOVIE_ID
import com.borisov.movies.ui.movies.adapter.MovieAdapter
import com.borisov.movies.utils.showSnakeBar
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @author Borisov Andrey on 27.06.2022
 **/

class MoviesFragment() : BaseFragment<FragmentMoviesBinding>(R.layout.fragment_movies),
    MovieAdapter.Delegate {

    private val viewModel: MoviesViewModel by viewModel()
    private val adapter by lazy { MovieAdapter(this) }

    override fun initListeners() {}

    override fun initObservers() {
        viewModel.getMoviesLiveData()
            .observe(viewLifecycleOwner) { res -> renderData(result = res) }
    }

    override fun renderSuccess(result: AppState.Success<*>) {
        showLoading(false)
        when (val moveResponse = result.data) {
            is MoviesResponse -> {
                renderMovies(moveResponse)
            }
        }
    }

    private fun renderMovies(moveResponse: MoviesResponse) {
        adapter.setItems(moveResponse.movies)
        if (adapter.itemCount > ZERO_VALUE)
            viewModel.setCurrentPage(moveResponse.page, moveResponse.totalPages)
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

    override fun onItemClick(movie: MoviesResponse.Movie) {
        NavHostFragment.findNavController(this).navigate(R.id.nav_detail, bundleOf().apply {
            putInt(KEY_MOVIE_ID, movie.id)
        })
    }

    override fun getMoreMovies() {
        viewModel.getMoviesTopRated(true)
    }

    override fun showLoading(isShow: Boolean) {
        viewBinding.progress.isVisible = isShow
    }

    override fun showError(throwable: Throwable) {
        viewBinding.root.showSnakeBar(throwable.localizedMessage)
    }

    companion object {
        private const val ZERO_VALUE = 0
    }
}