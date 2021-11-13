package com.uvita.andrey.modules.githubUsers

import com.uvita.andrey.modules.baseComponents.BaseView

interface GithubView : BaseView{
    fun onNetworkError(error:String)
}