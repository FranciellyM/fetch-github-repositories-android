package br.com.franmoraes.githubrepositories.base

import android.app.Application
import br.com.franmoraes.githubrepositories.data.remote.api.GithubRepositoriesApi
import br.com.franmoraes.githubrepositories.data.remote.di.MappersModule
import br.com.franmoraes.githubrepositories.data.repository.di.RepositoriesModule
import br.com.franmoraes.githubrepositories.presentation.di.PresentationModule
import com.nhaarman.mockitokotlin2.mock
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object TestSetup {

    fun start(baseUrl: String) {
        val context: Application = mock()

        org.koin.core.context.startKoin {
            androidContext(context)
            modules(
                configureNetworkModuleForTest(baseUrl),
                RepositoriesModule.module,
                MappersModule.module,
                PresentationModule.getModules().first()
            )
        }
    }

    private fun configureNetworkModuleForTest(baseApi: String) = module {
        factory {
            HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.HEADERS)
        }

        factory { OkHttpClient().newBuilder().addInterceptor(get<HttpLoggingInterceptor>()).build() }

        single {
            Retrofit.Builder()
                .client(get())
                .baseUrl(baseApi)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        factory { get<Retrofit>().create(GithubRepositoriesApi::class.java) }
    }
}