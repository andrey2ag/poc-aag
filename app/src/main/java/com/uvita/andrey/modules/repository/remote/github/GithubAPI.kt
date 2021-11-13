package com.uvita.andrey.modules.repository.remote.github

import retrofit2.http.GET
import com.uvita.andrey.models.pojos.GitHubUsersResponse
import retrofit2.Call
import retrofit2.http.Query

interface GithubAPI {
    @GET("search/users")
    fun getUsers(
        @Query("q") login: String,
        @Query("page") page: Int?,
        @Query("per_page") per_page: Int
    ): Call<GitHubUsersResponse?>
}