package br.com.franmoraes.githubrepositories.data.di

import br.com.franmoraes.githubrepositories.base.ModulePackage
import br.com.franmoraes.githubrepositories.data.remote.api.di.ApiModule
import br.com.franmoraes.githubrepositories.data.remote.api.di.BaseApiModule
import br.com.franmoraes.githubrepositories.data.remote.di.MappersModule
import br.com.franmoraes.githubrepositories.data.repository.di.RepositoriesModule
import org.koin.core.module.Module
import org.koin.dsl.module

internal object DataModule : ModulePackage {

    override fun getModules(): List<Module> = listOf(
        BaseApiModule.module,
        ApiModule.module,
        MappersModule.module,
        RepositoriesModule.module
    )
}