package br.com.franmoraes.githubrepositories.data.remote.api

import br.com.franmoraes.githubrepositories.data.remote.model.GithubRepositoriesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubRepositoriesApi {

    @GET("search/repositories")
    suspend fun fetchRepositories(
        @Query("q") language: String,
        @Query("sort") sort: String,
        @Query("page") page: Long
    ): GithubRepositoriesResponse
}