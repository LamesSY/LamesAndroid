package com.lames.standard.common

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding
import com.lames.standard.R

abstract class CommonDialogFragment<T : ViewBinding> : DialogFragment() {

    private var _binding: T? = null

    protected val binding: T get() = _binding!!

    protected abstract fun getViewBinding(inflater: LayoutInflater): T

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = getViewBinding(layoutInflater)
        return AlertDialog.Builder(requireActivity(), R.style.DialogFragmentTheme).setView(_binding!!.root).create()
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.let {
            it.setLayout(resources.displayMetrics.widthPixels * 8 / 10, it.attributes.height)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        initialization()
        bindEvent()
        doExtra()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    protected open fun initialization() {}
    protected open fun bindEvent() {}
    protected open fun doExtra() {}

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}