package com.borisov.movies.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.borisov.movies.domain.IAppState
import java.lang.reflect.ParameterizedType

/**
 * @author Borisov Andrey on 28.06.2022
 **/
abstract class BaseFragment<VB : ViewBinding>(
    @LayoutRes contentLayoutId: Int,
) : Fragment(contentLayoutId) {
    private var binding: VB? = null
    private val viewBinding: VB get() = binding!!

    /**
     * Инициализация слушателей
     */
    abstract fun initListeners()

    /**
     * Инициализация подписок
     */
    abstract fun initObservers()

    abstract fun renderData(result: IAppState)

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

        return viewBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}