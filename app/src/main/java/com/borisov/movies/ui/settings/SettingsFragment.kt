package com.borisov.movies.ui.settings

import com.borisov.movies.R
import com.borisov.movies.databinding.FragmentSettingsBinding
import com.borisov.movies.domain.AppState
import com.borisov.movies.domain.IAppState
import com.borisov.movies.ui.base.BaseFragment
import com.borisov.movies.utils.showSnakeBar
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @author Borisov Andrey on 27.06.2022
 **/
class SettingsFragment : BaseFragment<FragmentSettingsBinding>(R.layout.fragment_settings) {
    val viewModel: SettingsViewModel by viewModel()

    override fun initListeners() {}
    override fun initObservers() {}
    override fun renderSuccess(result: AppState.Success<*>) {}
    override fun showLoading(isShow: Boolean) {}
    override fun showError(throwable: Throwable) {
        viewBinding.root.showSnakeBar(throwable.localizedMessage)
    }
}