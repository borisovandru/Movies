package com.borisov.movies.ui.detail

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import org.koin.androidx.viewmodel.ext.android.viewModel
import  com.borisov.movies.BuildConfig
import  com.borisov.movies.R
import  com.borisov.movies.databinding.FragmentDetailBinding
import  com.borisov.movies.domain.AppState
import  com.borisov.movies.domain.models.ActorsResponse
import  com.borisov.movies.domain.models.MovieResponse
import  com.borisov.movies.ui.base.BaseFragment
import  com.borisov.movies.ui.detail.adapter.ActorAdapter
import  com.borisov.movies.utils.durationToString
import  com.borisov.movies.utils.getColorByValue
import  com.borisov.movies.utils.showSnakeBar
import  com.borisov.movies.utils.toDateString
import kotlin.math.roundToInt

/**
 * @author Borisov Andrey on 27.06.2022
 **/
class DetailFragment : BaseFragment<FragmentDetailBinding>(R.layout.fragment_detail),
    ActorAdapter.Delegate {

    private val viewModel: DetailViewModel by viewModel()
    private val movieId: Int? by lazy { arguments?.getInt(KEY_MOVIE_ID) }
    private val adapter by lazy { ActorAdapter(this) }

    override fun initListeners() {}
    override fun initObservers() {
        viewModel.getMovieLiveData()
            .observe(viewLifecycleOwner) { res -> renderData(result = res) }

        viewModel.getActorLiveData()
            .observe(viewLifecycleOwner) { res -> renderData(result = res) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerSetting()

        movieId?.let {
            viewModel.getMovieDetail(it)
            viewModel.getActors(it)
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

    @RequiresApi(Build.VERSION_CODES.N)
    override fun renderSuccess(result: AppState.Success<*>) {
        showLoading(false)
        when (val resultResponse = result.data) {
            is MovieResponse -> renderMovie(resultResponse)
            is ActorsResponse -> adapter.setItems(resultResponse.cast)
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun renderMovie(resultResponse: MovieResponse) {
        with(viewBinding) {
            title.text = resultResponse.title
            Glide.with(backdropImage)
                .load(BuildConfig.MOVIE_POSTER_PATH.plus(resultResponse.backdropPath))
                .apply(RequestOptions.bitmapTransform(RoundedCorners(IMAGE_RADIUS)))
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .transition(DrawableTransitionOptions().crossFade(DELAY))
                .error(R.drawable.ic_no_image)
                .into(backdropImage)
            Glide.with(poster)
                .load(BuildConfig.MOVIE_POSTER_PATH.plus(resultResponse.posterPath))
                .apply(RequestOptions.bitmapTransform(RoundedCorners(IMAGE_RADIUS)))
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .transition(DrawableTransitionOptions().crossFade(DELAY))
                .error(R.drawable.ic_no_image)
                .into(poster)
            val popular = (resultResponse.voteAverage * MULTIPLIER).roundToInt()
            ratingProgress.setProgress(popular, true)
            ratingValue.text = popular.toString()
            ratingProgress.setIndicatorColor(getColorByValue(popular))

            toDateString(resultResponse.releaseDate)?.let {
                release.text = it
            }
            resultResponse.runtime?.let {
                duration.text = durationToString(it)
            }
            genre.text =
                resultResponse.genres.joinToString(separator = ", ") { it ->
                    it.name.replaceFirstChar(
                        Char::titlecase
                    )
                }
            resultResponse.overview?.let {
                description.text = it
                descriptionTitle.isVisible = it.isNotEmpty()
            }
        }
    }

    private fun initRecyclerSetting() {
        viewBinding.recyclerView.also { recycler ->
            recycler.adapter = adapter
            recycler.setHasFixedSize(true)
        }
    }

    override fun showLoading(isShow: Boolean) {
        viewBinding.progress.isVisible = isShow
    }

    override fun onItemClick(actor: ActorsResponse.Cast) {}

    override fun showError(throwable: Throwable) {
        viewBinding.root.showSnakeBar(throwable.localizedMessage)
    }

    companion object {
        const val KEY_MOVIE_ID = "keyMovieId"
        private const val IMAGE_RADIUS = 18
        private const val MULTIPLIER = 10
        const val DELAY = 800
    }
}