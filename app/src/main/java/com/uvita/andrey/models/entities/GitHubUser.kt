package com.uvita.andrey.models.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "github_users")
class GitHubUser {

    @PrimaryKey(autoGenerate = true)
    var localId: Long = 0

    @SerializedName("id")
    var githubId: Long = 0

    @SerializedName("login")
    var login: String? = null

    @SerializedName("avatar_url")
    var avatarUrl: String = ""

    @SerializedName("html_url")
    var htmlUrl: String? = null

    @SerializedName("type")
    var type: String? = null

    override fun toString() = "id: $githubId login: $login"

}