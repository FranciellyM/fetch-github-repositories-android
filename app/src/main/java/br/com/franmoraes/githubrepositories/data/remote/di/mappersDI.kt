package br.com.franmoraes.githubrepositories.data.remote.di

import br.com.franmoraes.githubrepositories.data.mapper.GithubRepositoriesMapper
import br.com.franmoraes.githubrepositories.data.mapper.RepositoryOwnerMapper
import org.koin.core.module.Module

fun Module.mappersApi() {

    single {
        RepositoryOwnerMapper()
    }

    single {
        GithubRepositoriesMapper(ownerMapper = get())
    }
}