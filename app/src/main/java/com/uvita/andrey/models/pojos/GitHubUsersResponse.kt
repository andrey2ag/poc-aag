package com.uvita.andrey.models.pojos

import com.google.gson.annotations.SerializedName
import com.uvita.andrey.models.entities.GitHubUser

class GitHubUsersResponse {
    @SerializedName("total_count")
    var totalCount = 0
    @SerializedName("items")
    var users: List<GitHubUser> = emptyList()
}