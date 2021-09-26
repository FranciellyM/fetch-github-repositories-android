package br.com.franmoraes.githubrepositories.data.remote.model

import com.google.gson.annotations.SerializedName

data class RepositoryOwnerResponse(
    @SerializedName("avatar_url") val avatar: String,
    @SerializedName("login") val name: String
)