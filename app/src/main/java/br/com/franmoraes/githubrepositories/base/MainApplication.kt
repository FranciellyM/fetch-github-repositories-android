package br.com.franmoraes.githubrepositories.base

import android.app.Application
import br.com.franmoraes.githubrepositories.data.di.DataModule
import br.com.franmoraes.githubrepositories.presentation.di.PresentationModule
import org.koin.core.context.startKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MainApplication)
            modules(
                listOf(
                    PresentationModule.module,
                    DataModule.module
                )
            )
        }
    }
}