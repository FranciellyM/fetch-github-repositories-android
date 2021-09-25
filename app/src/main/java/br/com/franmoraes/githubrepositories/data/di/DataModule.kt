package br.com.franmoraes.githubrepositories.data.di

import br.com.franmoraes.githubrepositories.data.remote.api.di.apiDI
import br.com.franmoraes.githubrepositories.data.remote.api.di.insertBaseApi
import br.com.franmoraes.githubrepositories.data.remote.di.mappersApi
import br.com.franmoraes.githubrepositories.data.repository.di.repositories
import org.koin.dsl.module

internal object DataModule {
    val module = module {
        insertBaseApi()
        apiDI()
        mappersApi()
        repositories()
    }
}