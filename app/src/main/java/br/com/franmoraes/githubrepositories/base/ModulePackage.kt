package br.com.franmoraes.githubrepositories.base

import org.koin.core.module.Module

interface ModulePackage {
    fun getModules(): List<Module>
}