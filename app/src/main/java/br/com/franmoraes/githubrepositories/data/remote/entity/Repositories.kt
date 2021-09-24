package br.com.franmoraes.githubrepositories.data.remote.entity

data class Repositories(
    val repositoryId: Int,
    val fullName: String,
    val owner: RepositoryOwner,
    val forks: Int,
    val watchers: Int,
    val description: String
)