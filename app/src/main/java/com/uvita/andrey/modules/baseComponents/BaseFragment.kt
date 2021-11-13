package com.uvita.andrey.modules.baseComponents

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

abstract class BaseFragment<BViewModel, BBinding> : Fragment(), BaseView
        where BViewModel : BaseViewModel, BBinding : ViewDataBinding {
    protected lateinit var binding: BBinding
    protected lateinit var viewModel: BViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[getVMClass()]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initDatabinding(inflater, container)
        return binding.root
    }

    private fun initDatabinding(inflater: LayoutInflater, container: ViewGroup?) {
        binding = DataBindingUtil.inflate(inflater, layout(), container, false);
        binding.lifecycleOwner = this
    }

    @CallSuper
    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    @CallSuper
    override fun onStart() {
        super.onStart()
        viewModel.onStart()
    }

    @CallSuper
    override fun onStop() {
        super.onStop()
        viewModel.onStop()
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume()
    }

    protected abstract fun getVMClass(): Class<BViewModel>

    @LayoutRes
    protected abstract fun layout(): Int

    @StringRes
    protected abstract fun getToolbarTitle(): Int?

}