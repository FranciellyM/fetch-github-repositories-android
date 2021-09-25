package br.com.franmoraes.githubrepositories.data.remote.model

import com.google.gson.annotations.SerializedName

data class GithubRepositoriesResponse(
    @SerializedName("total_count") val totalItems: Int,
    @SerializedName("items") val repositories: List<RepositoriesResponse>?
)
