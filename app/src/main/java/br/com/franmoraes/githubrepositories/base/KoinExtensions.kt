package br.com.franmoraes.githubrepositories.base

import android.app.Application
import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.bind
import org.koin.dsl.module

/**
 * Add Context instance to Koin container
 * @param androidContext - Context
 */
fun KoinApplication.androidContext(androidContext: Context): KoinApplication {
    if (koin.logger.isAt(Level.INFO)) {
        koin.logger.info("[init] declare Android Context")
    }

    if (androidContext is Application) {
        koin.loadModules(listOf(module {
            single<Context> { androidContext } bind Application::class
        }))
    } else {
        koin.loadModules(listOf(module {
            single<Context> { androidContext }
        }))
    }

    return this
}

fun Application.startKoinWithModulePackages(vararg packages: ModulePackage) {

    startKoin {
        androidContext(this@startKoinWithModulePackages)
        modules(packages.map { it.getModules() }.reduce { acc, list -> acc + list })
    }
}