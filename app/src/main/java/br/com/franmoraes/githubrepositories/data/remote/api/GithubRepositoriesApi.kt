package br.com.franmoraes.githubrepositories.data.remote.api

import br.com.franmoraes.githubrepositories.data.remote.model.GithubRepositoriesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubRepositoriesApi {

    @GET("search/repositories")
    suspend fun fetchRepositories(
        @Query("language") language: String = "kotlin",
        @Query("sort") sort: String = "starts",
        @Query("page") page: Int
    ): GithubRepositoriesResponse
}