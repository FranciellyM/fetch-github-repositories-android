package br.com.franmoraes.githubrepositories.data.remote.api.di

import br.com.franmoraes.githubrepositories.data.remote.api.BuildInfoHelper
import br.com.franmoraes.githubrepositories.data.remote.api.CacheInterceptor
import com.google.gson.Gson
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val KOIN_WEB_API_URL = "webApiUrl"
const val KOIN_RETROFIT = "retrofit"
const val KOIN_IS_DEBUG = "debug_mode"
const val KOIN_OKHTTP = "okhttp"

const val CACHE_SIZE = (10 * 1024 * 1024).toLong()
const val CACHE_MAX_AGE = 30
val CACHE_TIME_UNIT = TimeUnit.MINUTES

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
                .addNetworkInterceptor(CacheInterceptor(CACHE_MAX_AGE, CACHE_TIME_UNIT))
                .cache(Cache(androidApplication().cacheDir, CACHE_SIZE))
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