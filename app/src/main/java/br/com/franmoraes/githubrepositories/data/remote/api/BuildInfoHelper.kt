package br.com.franmoraes.githubrepositories.data.remote.api

import br.com.franmoraes.githubrepositories.BuildConfig

class BuildInfoHelper {

    val webApiUrl: String
        get() = "https://api.github.com/"

    //https://api.github.com/search/repositories?q=language:kotlin&sort=stars&page=1

    val isDebug: Boolean
        get() = BuildConfig.DEBUG
}