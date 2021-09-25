package br.com.franmoraes.githubrepositories.data.remote.api

import br.com.franmoraes.githubrepositories.BuildConfig

class BuildInfoHelper {

    val webApiUrl: String
        get() = "https://api.github.com/"

    val isDebug: Boolean
        get() = BuildConfig.DEBUG
}