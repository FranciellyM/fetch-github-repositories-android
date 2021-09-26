package br.com.franmoraes.githubrepositories.data.remote.entity

data class GithubRepositories(
    val totalItems: Int,
    val repositories: List<Repositories> = emptyList()
)