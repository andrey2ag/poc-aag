package com.uvita.andrey.modules.githubUsers

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.uvita.andrey.R
import com.uvita.andrey.databinding.FragmentGithubUsersBinding
import com.uvita.andrey.models.entities.GitHubUser
import com.uvita.andrey.modules.baseComponents.BaseFragment
import com.uvita.andrey.utils.gone
import com.uvita.andrey.utils.isVisible
import com.uvita.andrey.utils.visible


class GithubFragment : BaseFragment<GithubViewModel, FragmentGithubUsersBinding>(), GitHubUsersPresenter, GithubView {

    // Setup List
    private var listAdapter = UserListListAdapter()

    override fun getVMClass() = GithubViewModel::class.java

    override fun layout() = R.layout.fragment_github_users

    override fun getToolbarTitle() = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.presenter = this
        viewModel.view = this
        setupPaging()
        setupInput()
    }

    private val dataObserver = Observer<List<GitHubUser>> {
        listAdapter.setList(it)
        with(binding) {
            loading.gone()
            newRequestLoading.postDelayed({
                if (newRequestLoading.isVisible()) {
                    newRequestLoading.gone()
                }
            }, 1000L)
        }
    }

    override fun onSearchClick() {
        if (binding.loginInput.text?.isNotEmpty() == true) {
            hideSoftKeyboard(binding.loginInput)
            binding.newRequestLoading.visible()
            viewModel.newRequest(binding.loginInput.text.toString()).observe(viewLifecycleOwner, dataObserver)
        }else{
            binding.loginInput.error = "Please provide a login name"
        }
    }

    private fun setupInput() {
        with(binding) {
            newRequestLoading.gone()
            loading.gone()
            loginInput.setOnEditorActionListener { _, actionId, _ ->
                return@setOnEditorActionListener when (actionId) {
                    EditorInfo.IME_ACTION_SEARCH -> {
                        onSearchClick()
                        true
                    }
                    else -> false
                }
            }
        }
    }

    private fun setupPaging() {
        // setup list
        with(binding.usersRecycler) {
            layoutManager = LinearLayoutManager(context)
            adapter = listAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    // TODO validate this does not trigger twice
                    if (!recyclerView.canScrollVertically(RecyclerView.FOCUS_DOWN)) {
                        binding.loading.visible()
                        viewModel.loadMoreUsers()
                    }
                }
            })
        }
    }

    private fun hideSoftKeyboard(view: View) {
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    companion object {
        @JvmStatic
        fun newInstance() = GithubFragment()
    }

    override fun onNetworkError(error: String) {
        with(binding) {
            Snackbar.make(root, error, 4000).show()
            loading.gone()
            newRequestLoading.gone()
        }
    }
}