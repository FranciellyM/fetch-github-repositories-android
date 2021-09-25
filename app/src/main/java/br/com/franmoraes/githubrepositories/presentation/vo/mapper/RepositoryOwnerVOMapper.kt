package br.com.franmoraes.githubrepositories.presentation.vo.mapper

import br.com.franmoraes.githubrepositories.data.mapper.BaseMapper
import br.com.franmoraes.githubrepositories.data.remote.entity.RepositoryOwner
import br.com.franmoraes.githubrepositories.data.remote.model.RepositoryOwnerResponse
import br.com.franmoraes.githubrepositories.presentation.vo.RepositoryOwnerVO

class RepositoryOwnerVOMapper : BaseMapper<RepositoryOwner, RepositoryOwnerVO>() {

    override fun transform(inputObject: RepositoryOwner): RepositoryOwnerVO =
        RepositoryOwnerVO(
            avatar = inputObject.avatar,
            name = inputObject.name
        )
}