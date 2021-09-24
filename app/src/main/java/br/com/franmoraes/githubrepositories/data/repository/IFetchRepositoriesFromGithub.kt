package br.com.franmoraes.githubrepositories.data.repository

import br.com.franmoraes.githubrepositories.data.remote.entity.GithubRepositories

interface IFetchRepositoriesFromGithub {
    suspend fun fetchRepositories(pageNumber: Int = 0) : GithubRepositories
}