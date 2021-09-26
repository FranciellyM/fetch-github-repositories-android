package br.com.franmoraes.githubrepositories.data.remote.model

import com.google.gson.annotations.SerializedName

data class RepositoriesResponse(
    @SerializedName("id") val repositoryId: Int,
    @SerializedName("full_name") val fullName: String,
    @SerializedName("name") val name: String,
    @SerializedName("owner") val owner: RepositoryOwnerResponse,
    @SerializedName("forks_count") val forks: Int?,
    @SerializedName("stargazers_count") val watchers: Int?,
    @SerializedName("description") val description: String?
)
