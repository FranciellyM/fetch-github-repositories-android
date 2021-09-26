package br.com.franmoraes.githubrepositories.data.di

import br.com.franmoraes.githubrepositories.data.remote.api.di.apiDI
import br.com.franmoraes.githubrepositories.data.remote.api.di.insertBaseApi
import br.com.franmoraes.githubrepositories.data.remote.di.MappersModule
import br.com.franmoraes.githubrepositories.data.repository.di.RepositoriesModule
import org.koin.dsl.module

internal object DataModule {
    val module = module {
        insertBaseApi()
        apiDI()
        MappersModule.module
        RepositoriesModule.module
    }
}