package br.com.franmoraes.githubrepositories.data.repository.di

import br.com.franmoraes.githubrepositories.data.repository.FetchRepositoriesFromGithub
import br.com.franmoraes.githubrepositories.data.repository.IFetchRepositoriesFromGithub
import org.koin.dsl.module

internal object RepositoriesModule {
    val module = module {

        single<IFetchRepositoriesFromGithub> {
            FetchRepositoriesFromGithub(
                api = get(),
                mapper = get()
            )
        }
    }
}