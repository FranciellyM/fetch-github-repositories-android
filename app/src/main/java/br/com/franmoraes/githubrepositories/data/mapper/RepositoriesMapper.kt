package br.com.franmoraes.githubrepositories.data.mapper

import br.com.franmoraes.githubrepositories.data.remote.entity.Repositories
import br.com.franmoraes.githubrepositories.data.remote.model.RepositoriesResponse

class RepositoriesMapper(
    private val ownerMapper: RepositoryOwnerMapper
) : BaseMapper<RepositoriesResponse, Repositories>() {

    override fun transform(inputObject: RepositoriesResponse): Repositories =
        Repositories(
            repositoryId = inputObject.repositoryId,
            fullName = inputObject.fullName,
            name = inputObject.name,
            owner = ownerMapper.transform(inputObject = inputObject.owner),
            forks = inputObject.forks ?: 0,
            watchers = inputObject.watchers ?: 0,
            description = inputObject.description.orEmpty()
        )
}