package br.com.franmoraes.githubrepositories.presentation.vo

data class GithubRepositoriesVO(
    val fullName: String,
    val name: String,
    val owner: RepositoryOwnerVO,
    val forks: Int,
    val watchers: Int,
    val description: String
)