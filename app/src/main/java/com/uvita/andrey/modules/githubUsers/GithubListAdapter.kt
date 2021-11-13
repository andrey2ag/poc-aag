package com.uvita.andrey.modules.githubUsers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.uvita.andrey.R
import com.uvita.andrey.general.LogUtil
import com.uvita.andrey.models.entities.GitHubUser
import com.uvita.andrey.modules.githubUsers.viewHolder.GitUserViewHolder
import com.uvita.andrey.modules.githubUsers.viewHolder.BaseViewHolder
import java.util.*


const val VIEW_TYPE_USER = 1

class UserListListAdapter : RecyclerView.Adapter<BaseViewHolder>() {

    init {
        setHasStableIds(true)
    }

    private var users: List<GitHubUser> = Collections.emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when (viewType) {
            VIEW_TYPE_USER -> GitUserViewHolder(inflate(R.layout.view_holder_user, parent))
            else -> GitUserViewHolder(inflate(R.layout.view_holder_user, parent)) // todo:  Loading?
        }
    }

    private fun inflate(@LayoutRes layoutId: Int, parent: ViewGroup) =
        LayoutInflater.from(parent.context).inflate(layoutId, parent, false)

    fun setList(usersList: List<GitHubUser>) {
        LogUtil.LOGD(TAG, "#users; [setList] count:" + users.size)
        users = usersList// usersList.sortedBy { it.login }
        notifyDataSetChanged()
    }

    override fun getItemId(position: Int) = users[position].githubId

    override fun getItemCount() = users.size

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(users[position], (itemCount - 1) == position)
    }

    override fun getItemViewType(position: Int): Int = 0 // TODO define type? users[position].type

    companion object {
        const val TAG = "UserListListAdapter"
    }

}