package br.com.franmoraes.githubrepositories.data.repository.di

import br.com.franmoraes.githubrepositories.data.repository.FetchRepositoriesFromGithub
import br.com.franmoraes.githubrepositories.data.repository.IFetchRepositoriesFromGithub
import org.koin.core.module.Module

fun Module.repositories() {

    single<IFetchRepositoriesFromGithub> {
        FetchRepositoriesFromGithub(
            api = get(),
            mapper = get()
        )
    }
}