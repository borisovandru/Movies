package com.borisov.movies.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.borisov.movies.domain.AppState
import com.borisov.movies.domain.IAppState
import com.borisov.movies.ui.ToolbarListener
import java.lang.reflect.ParameterizedType

/**
 * @author Borisov Andrey on 28.06.2022
 **/
abstract class BaseFragment<VB : ViewBinding>(
    @LayoutRes contentLayoutId: Int,
) : Fragment(contentLayoutId) {
    private var binding: VB? = null
    val viewBinding: VB get() = binding!!

    /**
     * Инициализация слушателей
     */
    abstract fun initListeners()

    /**
     * Инициализация подписок
     */
    abstract fun initObservers()

    protected fun renderData(result: IAppState) {
        when (result) {
            is AppState.Error -> {
                showLoading(false)
                showError(result.error)
            }
            is AppState.Loading -> showLoading(true)
            is AppState.Success<*> -> renderSuccess(result)
        }
    }

    abstract fun renderSuccess(result: AppState.Success<*>)
    abstract fun showLoading(isShow: Boolean)
    abstract fun showError(throwable: Throwable)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        ((javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<*>)
            .getMethod(
                "inflate",
                LayoutInflater::class.java,
                ViewGroup::class.java,
                Boolean::class.java
            ).also {
                binding = it.invoke(null, layoutInflater, container, false) as VB
            }

        initObservers()
        initListeners()
        return viewBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    protected fun showToolBar(visible: Boolean) {
        (requireActivity() as ToolbarListener).showToolBar(visible)
    }
}