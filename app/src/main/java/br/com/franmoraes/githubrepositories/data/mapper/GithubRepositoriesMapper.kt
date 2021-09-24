package br.com.franmoraes.githubrepositories.data.mapper

import br.com.franmoraes.githubrepositories.data.remote.entity.GithubRepositories
import br.com.franmoraes.githubrepositories.data.remote.model.GithubRepositoriesResponse

class GithubRepositoriesMapper(
    private val ownerMapper: RepositoryOwnerMapper
) : BaseMapper<GithubRepositoriesResponse, GithubRepositories>() {

    override fun transform(inputObject: GithubRepositoriesResponse): GithubRepositories =
        GithubRepositories(
            repositoryId = inputObject.repositoryId,
            fullName = inputObject.fullName,
            owner = ownerMapper.transform(inputObject = inputObject.owner),
            forks = inputObject.forks?.let { it } ?: 0,
            watchers = inputObject.watchers?.let { it } ?: 0,
            description = inputObject.description.orEmpty()
        )
}