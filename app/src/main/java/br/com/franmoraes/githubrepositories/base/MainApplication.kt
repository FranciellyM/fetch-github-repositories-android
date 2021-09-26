package br.com.franmoraes.githubrepositories.base

import android.app.Application
import br.com.franmoraes.githubrepositories.data.di.DataModule
import br.com.franmoraes.githubrepositories.presentation.di.PresentationModule

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoinWithModulePackages(
            PresentationModule,
            DataModule
        )
    }
}