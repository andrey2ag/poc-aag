package com.uvita.andrey.modules.githubUsers

import androidx.lifecycle.LiveData
import com.uvita.andrey.general.LogUtil
import com.uvita.andrey.models.entities.GitHubUser
import com.uvita.andrey.modules.baseComponents.BaseViewModel
import com.uvita.andrey.modules.repository.local.AppDB
import com.uvita.andrey.modules.repository.remote.RepositoryStateCallbacks
import com.uvita.andrey.modules.repository.remote.github.GithubRepository

class GithubViewModel : BaseViewModel(), RepositoryStateCallbacks {
    private val githubRepository = GithubRepository(this, AppDB.instance)
    var view: GithubView? = null

    fun loadMoreUsers() {
        LogUtil.LOGD(TAG, "#users; [loadMoreUsers]")
        githubRepository.loadMoreUsers()
    }

    fun newRequest(login: String): LiveData<List<GitHubUser>> {
        LogUtil.LOGD(TAG, "#users; [newRequest]")
        return githubRepository.newRequest(login)
    }

    override fun onRepositoryError(error: String) {
        view?.onNetworkError(error)
    }

    companion object {
        const val TAG = "GithubViewModel"
    }

}