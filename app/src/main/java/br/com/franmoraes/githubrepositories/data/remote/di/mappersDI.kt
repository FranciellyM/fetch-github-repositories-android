package br.com.franmoraes.githubrepositories.data.remote.di

import br.com.franmoraes.githubrepositories.data.mapper.GithubRepositoriesMapper
import br.com.franmoraes.githubrepositories.data.mapper.RepositoriesMapper
import br.com.franmoraes.githubrepositories.data.mapper.RepositoryOwnerMapper
import org.koin.dsl.module

internal object MappersModule {
    val module = module {
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
}