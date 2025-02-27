package com.vasyl.code.vasylchallenge.data.remote.model.procedure


import com.google.gson.annotations.SerializedName
import com.vasyl.code.vasylchallenge.data.remote.model.icon.IconDto

data class ProcedureDto(
    @SerializedName("uuid")
    val uuid: String,
    @SerializedName("icon")
    val icon: IconDto?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("phases")
    val phases: List<String>?
)

data class ProcedureDetailsDto(
    @SerializedName("uuid")
    val uuid: String,
    @SerializedName("name")
    val name: String?,
    @SerializedName("phases")
    val phases: List<PhaseDto>?,
    @SerializedName("card")
    val card: IconDto?,
    @SerializedName("date_published")
    val datePublished: String?,
    @SerializedName("duration")
    val duration: Int?
)

data class PhaseDto(
    @SerializedName("uuid")
    val uuid: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("icon")
    val icon: IconDto?
)