package br.com.franmoraes.githubrepositories.presentation.ui.repolist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.franmoraes.githubrepositories.databinding.RepoListItemBinding
import br.com.franmoraes.githubrepositories.presentation.vo.GithubRepositoriesVO

class ReposAdapter(private var reposList: List<GithubRepositoriesVO>) :
    RecyclerView.Adapter<ReposVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReposVH {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ReposVH(RepoListItemBinding.inflate(layoutInflater, parent, false))
    }

    override fun getItemCount(): Int {
        return reposList.size
    }

    override fun onBindViewHolder(holder: ReposVH, position: Int) {
        holder.bind(getItem(position))
    }

    private fun getItem(position: Int) = reposList[position]

    fun updateList(newList: List<GithubRepositoriesVO>) {
        reposList = newList
        notifyDataSetChanged()
    }

    companion object : DiffUtil.ItemCallback<GithubRepositoriesVO>() {
        override fun areContentsTheSame(
            oldItem: GithubRepositoriesVO,
            newItem: GithubRepositoriesVO
        ): Boolean = oldItem == newItem

        override fun areItemsTheSame(
            oldItem: GithubRepositoriesVO,
            newItem: GithubRepositoriesVO
        ): Boolean = oldItem.fullName == newItem.fullName
    }
}