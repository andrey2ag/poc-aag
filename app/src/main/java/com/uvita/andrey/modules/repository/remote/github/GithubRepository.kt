package com.uvita.andrey.modules.repository.remote.github

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import com.uvita.andrey.general.LogUtil
import com.uvita.andrey.models.entities.GitHubUser
import com.uvita.andrey.models.pojos.GitHubUsersResponse
import com.uvita.andrey.modules.repository.local.AppDB
import com.uvita.andrey.modules.repository.remote.BaseRepository
import com.uvita.andrey.modules.repository.remote.RepositoryStateCallbacks
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.HttpURLConnection

const val PER_PAGE = 9 // 9 items per page from requirements
const val FIRST_PAGE = 1

class GithubRepository(val stateCallbacks: RepositoryStateCallbacks, private val dbClient: AppDB ) : BaseRepository() {
    private val restInterface: GithubAPI
        get() = retrofit.create(GithubAPI::class.java)

    private var currentLogin: String = ""
    private var currentPage: Int = FIRST_PAGE
    private var loadedEntries: Int = 0
    private var totalEntries: Int = 0

    private var usersResponseCallback: Callback<GitHubUsersResponse?> = object : Callback<GitHubUsersResponse?> {
        override fun onResponse(call: Call<GitHubUsersResponse?>, response: Response<GitHubUsersResponse?>) {
            if (response.code() == HttpURLConnection.HTTP_OK) {
                response.body()?.let {
                    LogUtil.LOGD(TAG, "#users; Total:" + it.totalCount)
                    totalEntries = it.totalCount
                    loadedEntries += it.users.size
                    storeRegisters(it.users)
                }
            } else if (response.code() == HttpURLConnection.HTTP_FORBIDDEN) {
                LogUtil.LOGE(TAG, "#users; Error response:" + response.errorBody())
                if (currentPage > FIRST_PAGE) // rollback the page if the request limit were exceeded
                    currentPage--

                stateCallbacks.onRepositoryError("Request rate limit exceeded. Please retry in 10 seconds")
            }
        }

        override fun onFailure(call: Call<GitHubUsersResponse?>, t: Throwable) {
            LogUtil.LOGE(TAG, "#users; Error getting users.", t)
            stateCallbacks.onRepositoryError("Error requesting the information")
        }
    }

    fun loadMoreUsers() {
        if (loadedEntries < totalEntries) {
            currentPage++
            getGitHubUsers(currentLogin, currentPage)
        } else if (totalEntries > 0) {
            stateCallbacks.onRepositoryError("End of results")
        }
    }

    fun newRequest(login: String): LiveData<List<GitHubUser>> {
        cleanAppDB()
        currentPage = FIRST_PAGE
        totalEntries = 0
        loadedEntries = 0
        currentLogin = login
        return getGitHubUsers(login, FIRST_PAGE)
    }

    @VisibleForTesting
    fun getGitHubUsers(login: String, page: Int): LiveData<List<GitHubUser>> {
        restInterface.getUsers("$login in:login", page, PER_PAGE).enqueue(usersResponseCallback)
        LogUtil.LOGD(TAG, "#users; login:$login page:$page")
        return dbClient.githubDao().getUsers()
    }

    @VisibleForTesting
    fun cleanAppDB() {
        dbClient.githubDao().removeAllEntries()
    }

    private fun storeRegisters(gitHubUsers: List<GitHubUser>) {
        dbClient.githubDao().insertAll(gitHubUsers)
    }

    companion object {
        private const val TAG = "GithubRepository"
    }
}