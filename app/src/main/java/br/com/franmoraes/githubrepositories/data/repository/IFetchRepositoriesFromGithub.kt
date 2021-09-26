package br.com.franmoraes.githubrepositories.data.repository

import br.com.franmoraes.githubrepositories.data.remote.entity.Repositories

interface IFetchRepositoriesFromGithub {
    suspend fun fetchRepositories(
        pageNumber: Long = 0,
        language: String = "kotlin",
        sort: String = "starts"
    ): List<Repositories>
}