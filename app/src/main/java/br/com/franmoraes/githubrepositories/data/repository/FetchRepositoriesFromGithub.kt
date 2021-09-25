package br.com.franmoraes.githubrepositories.data.repository

import br.com.franmoraes.githubrepositories.data.mapper.GithubRepositoriesMapper
import br.com.franmoraes.githubrepositories.data.remote.api.GithubRepositoriesApi
import br.com.franmoraes.githubrepositories.data.remote.entity.Repositories

class FetchRepositoriesFromGithub(
    private val api: GithubRepositoriesApi,
    private val mapper: GithubRepositoriesMapper
) : IFetchRepositoriesFromGithub {

    override suspend fun fetchRepositories(
        pageNumber: Long,
        language: String,
        sort: String
    ): List<Repositories> {
        val githubRepositories = api.fetchRepositories(
            page = pageNumber, language = "language:$language", sort = sort
        )
        return mapper.transform(githubRepositories).repositories
    }
}