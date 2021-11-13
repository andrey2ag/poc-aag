package com.uvita.andrey.modules.githubUsers.viewHolder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.uvita.andrey.models.entities.GitHubUser
import java.text.SimpleDateFormat
import java.util.*

abstract class BaseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun bind(user: GitHubUser, isLastElement: Boolean)
}