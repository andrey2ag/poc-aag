package com.uvita.andrey.modules.repository.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.uvita.andrey.models.entities.GitHubUser

@Dao
abstract class GitHubDao : BaseDao<GitHubUser>() {

    @Query("Select * from github_users order by localId")
    abstract fun getUsers(): LiveData<List<GitHubUser>>

    @Query("Delete from github_users")
    abstract fun removeAllEntries()
}