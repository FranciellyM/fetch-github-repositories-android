package br.com.franmoraes.githubrepositories.base

import android.app.Application
import org.koin.core.context.startKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MainApplication)
            modules(
                listOf(

                )
            )
        }
    }
}