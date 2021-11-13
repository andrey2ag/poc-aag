package com.uvita.andrey.modules.githubUsers.viewHolder

import android.content.Intent
import android.net.Uri
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.DataBindingUtil
import com.uvita.andrey.databinding.ViewHolderUserBinding
import com.uvita.andrey.models.entities.GitHubUser


class GitUserViewHolder(view: View) : BaseViewHolder(view) {

    private val binding: ViewHolderUserBinding = DataBindingUtil.bind(view)!!

    override fun bind(user: GitHubUser, isLastElement: Boolean) {
        binding.userLogin.text = user.login
        val type = user.type
        binding.type.text = "Type: $type"
        binding.imgAvatar.setImageURI(user.avatarUrl)

        binding.root.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(user.htmlUrl)
            startActivity(it.context, intent, null)
        }

        binding.separator.visibility = if (!isLastElement) VISIBLE else GONE
    }
}