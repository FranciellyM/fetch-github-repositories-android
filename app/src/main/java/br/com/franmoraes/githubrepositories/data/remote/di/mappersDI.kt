package br.com.franmoraes.githubrepositories.data.remote.di

import br.com.franmoraes.githubrepositories.data.mapper.GithubRepositoriesMapper
import br.com.franmoraes.githubrepositories.data.mapper.RepositoriesMapper
import br.com.franmoraes.githubrepositories.data.mapper.RepositoryOwnerMapper
import org.koin.core.module.Module

fun Module.mappersApi() {

    single {
        GithubRepositoriesMapper(repositoriesMapper = get())
    }

    single {
        RepositoryOwnerMapper()
    }

    single {
        RepositoriesMapper(ownerMapper = get())
    }
}