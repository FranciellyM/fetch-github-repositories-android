package br.com.franmoraes.githubrepositories.data.mapper

import br.com.franmoraes.githubrepositories.data.remote.entity.GithubRepositories
import br.com.franmoraes.githubrepositories.data.remote.model.GithubRepositoriesResponse

class GithubRepositoriesMapper(
    private val repositoriesMapper: RepositoriesMapper
) : BaseMapper<GithubRepositoriesResponse, GithubRepositories>() {

    override fun transform(inputObject: GithubRepositoriesResponse): GithubRepositories =
        GithubRepositories(
            totalItems = inputObject.totalItems,
            repositories = inputObject.repositories?.map { repository ->
                repositoriesMapper.transform(
                    inputObject = repository
                )
            }.orEmpty()
        )
}