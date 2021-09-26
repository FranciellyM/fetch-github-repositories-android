package br.com.franmoraes.githubrepositories.data.remote.api.di

import br.com.franmoraes.githubrepositories.data.remote.api.BuildInfoHelper
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val KOIN_WEB_API_URL = "webApiUrl"
const val KOIN_RETROFIT = "retrofit"
const val KOIN_IS_DEBUG = "debug_mode"
const val KOIN_OKHTTP = "okhttp"

internal object BaseApiModule {
    val module = module {
        // Retrofit Injection
        single(named(KOIN_RETROFIT)) {
            Retrofit
                .Builder()
                .baseUrl(get<String>(named(KOIN_WEB_API_URL)))
                .addConverterFactory(get<GsonConverterFactory>())
                .client(get(named(KOIN_OKHTTP)))
                .build()
        }

        single(named(KOIN_WEB_API_URL)) {
            get<BuildInfoHelper>().webApiUrl
        }

        single {
            BuildInfoHelper()
        }

        single {
            GsonConverterFactory.create(get())
        }

        single {
            Gson()
        }

        single(named(KOIN_OKHTTP)) {
            OkHttpClient().newBuilder()
                .addInterceptor(get<HttpLoggingInterceptor>())
                .build()
        }

        single {
            HttpLoggingInterceptor().apply {
                level =
                    if (get(named(KOIN_IS_DEBUG))) HttpLoggingInterceptor.Level.BODY
                    else HttpLoggingInterceptor.Level.NONE
            }
        }

        single(named(KOIN_IS_DEBUG)) {
            get<BuildInfoHelper>().isDebug
        }
    }
}