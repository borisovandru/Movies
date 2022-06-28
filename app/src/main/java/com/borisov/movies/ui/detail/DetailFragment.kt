package com.borisov.movies.ui.detail

import com.borisov.movies.R
import com.borisov.movies.databinding.FragmentDetailBinding
import com.borisov.movies.domain.IAppState
import com.borisov.movies.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @author Borisov Andrey on 27.06.2022
 **/
class DetailFragment : BaseFragment<FragmentDetailBinding>(R.layout.fragment_detail) {

    val viewModel: DetailViewModel by viewModel()

    override fun initListeners() {}

    override fun initObservers() {}

    override fun renderData(result: IAppState) {}
}