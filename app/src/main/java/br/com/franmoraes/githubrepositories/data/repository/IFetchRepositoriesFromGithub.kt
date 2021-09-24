package br.com.franmoraes.githubrepositories.data.repository

import br.com.franmoraes.githubrepositories.data.remote.entity.Repositories

interface IFetchRepositoriesFromGithub {
    suspend fun fetchRepositories(pageNumber: Int = 0) : List<Repositories>
}