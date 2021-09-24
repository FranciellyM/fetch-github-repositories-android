package br.com.franmoraes.githubrepositories.data.remote.api.di

import br.com.franmoraes.githubrepositories.data.remote.api.GithubRepositoriesApi
import br.com.franmoraes.githubrepositories.data.remote.api.ServiceFactory
import org.koin.core.module.Module
import org.koin.core.qualifier.named


fun Module.apiDI() {

    single {
        ServiceFactory.createService(get(named(KOIN_RETROFIT)), GithubRepositoriesApi::class.java)
    }

}