package br.com.franmoraes.githubrepositories.data.repository.di

import br.com.franmoraes.githubrepositories.data.mapper.RepositoriesMapper
import br.com.franmoraes.githubrepositories.data.mapper.RepositoryOwnerMapper
import org.koin.core.module.Module

fun Module.repositoriesApi() {

    single {
        RepositoryOwnerMapper()
    }

    single {
        RepositoriesMapper(ownerMapper = get())
    }
}