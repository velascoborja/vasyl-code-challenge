package com.vasyl.code.vasylchallenge.data.remote.model.icon


import com.google.gson.annotations.SerializedName

data class IconDto(
    @SerializedName("uuid")
    val uuid: String?,
    @SerializedName("url")
    val url: String?,
    @SerializedName("version")
    val version: Int?
)