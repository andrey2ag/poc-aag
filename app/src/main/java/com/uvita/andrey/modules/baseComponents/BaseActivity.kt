package com.uvita.andrey.modules.baseComponents

import android.os.Bundle
import android.widget.TextView
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.uvita.andrey.R

abstract class BaseActivity<BViewModel, BBinding> : AppCompatActivity(), BaseView
        where BViewModel : BaseViewModel, BBinding : ViewDataBinding {

    protected lateinit var binding: BBinding
    private lateinit var viewModel: BViewModel

    protected abstract fun getVMClass(): Class<BViewModel>

    @LayoutRes
    protected abstract fun layout(): Int

    @StringRes
    protected abstract fun getToolbarTitle(): Int?

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme_NoActionBar)
        super.onCreate(savedInstanceState)
        // initialize Data-binding must be called before getViewModel() because to generate the viewModel needs the UI data-binding TODO ?
        initBinding()
        viewModel = ViewModelProvider(this)[getVMClass()]
        setupToolbar()

    }

    /**
     * Initialise databinding and kick off variable binding
     */
    private fun initBinding() {
        binding = DataBindingUtil.setContentView(this, layout())
        binding.lifecycleOwner = this
    }

    private fun setupToolbar() = getToolbarTitle()?.let {
        setToolbarTitle(resources.getText(it).toString())
    }

    protected fun setToolbarTitle(title: String?) {
        // check because not all activities use toolbar... ex, LoginActivity
        binding.root.findViewById<Toolbar?>(R.id.toolbar)?.let {
            val textView = it.findViewById<TextView>(R.id.tv_title)
            textView.text = title
            setSupportActionBar(it)
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.onStart()
    }

    override fun onStop() {
        super.onStop()
        viewModel.onStop()
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume()
    }
}