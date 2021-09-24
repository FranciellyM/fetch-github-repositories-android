package br.com.franmoraes.githubrepositories.data.mapper

import br.com.franmoraes.githubrepositories.data.remote.entity.RepositoryOwner
import br.com.franmoraes.githubrepositories.data.remote.model.RepositoryOwnerResponse

class RepositoryOwnerMapper : BaseMapper<RepositoryOwnerResponse, RepositoryOwner>() {

    override fun transform(inputObject: RepositoryOwnerResponse): RepositoryOwner =
        RepositoryOwner(
            avatar = inputObject.avatar,
            name = inputObject.name
        )
}