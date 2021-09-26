package br.com.franmoraes.githubrepositories.presentation.ui.repolist.adapter

import androidx.recyclerview.widget.RecyclerView
import br.com.franmoraes.githubrepositories.R
import br.com.franmoraes.githubrepositories.databinding.RepoListItemBinding
import br.com.franmoraes.githubrepositories.presentation.vo.GithubRepositoriesVO
import com.squareup.picasso.Picasso

class ReposVH(
    private val binding: RepoListItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    open fun bind(githubRepoVO: GithubRepositoriesVO) {
        Picasso.get().load(githubRepoVO.owner.avatar)
            .placeholder(R.drawable.ic_account_circle)
            .into(binding.itemProfileImg)

        binding.itemRepoName.text = githubRepoVO.name
        binding.itemAuthorName.text = githubRepoVO.owner.name
        binding.itemDescription.text = githubRepoVO.description
        binding.itemStarLabel.text = githubRepoVO.watchers.toString()
        binding.itemCodeForkLabel.text = githubRepoVO.forks.toString()
    }
}