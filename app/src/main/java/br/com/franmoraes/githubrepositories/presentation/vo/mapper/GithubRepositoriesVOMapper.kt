package br.com.franmoraes.githubrepositories.presentation.vo.mapper

import br.com.franmoraes.githubrepositories.data.mapper.BaseMapper
import br.com.franmoraes.githubrepositories.data.remote.entity.Repositories
import br.com.franmoraes.githubrepositories.presentation.vo.GithubRepositoriesVO

class GithubRepositoriesVOMapper(
    private val ownerMapper: RepositoryOwnerVOMapper
) : BaseMapper<Repositories, GithubRepositoriesVO>() {

    override fun transform(inputObject: Repositories): GithubRepositoriesVO =
        GithubRepositoriesVO(
            fullName = inputObject.fullName,
            name = inputObject.name,
            owner = ownerMapper.transform(inputObject = inputObject.owner),
            forks = inputObject.forks?.let { it } ?: 0,
            watchers = inputObject.watchers?.let { it } ?: 0,
            description = inputObject.description.orEmpty()
        )
}