package com.lames.standard.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.viewbinding.ViewBinding
import com.lames.standard.R

abstract class CommonFragment<T : ViewBinding> : Fragment() {

    var containerId: Int = -1
        private set

    protected val dispatcher by lazy { requireActivity().onBackPressedDispatcher }

    private var _binding: T? = null

    protected val binding: T get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = getViewBinding(inflater, container)
        containerId = container?.id ?: -1
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialization()
        bindEvent()
        doExtra()
    }

    protected abstract fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): T

    protected open fun initialization() {}
    protected open fun bindEvent() {}
    protected open fun doExtra() {}

    protected inline fun <reified T : CommonFragment<*>> start(
        containerId: Int = this.containerId,
        args: Bundle? = null,
        tag: String? = null,
        addToBackStack: Boolean = true,
    ) {
        requireActivity().supportFragmentManager.commit {
            setCustomAnimations(R.anim.slide_in, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out)
            add(containerId, T::class.java, args)
            if (addToBackStack) addToBackStack(tag)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}