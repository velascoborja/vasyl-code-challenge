package com.vasyl.code.vasylchallenge.domain.model


data class ProcedureDetails(
    val uuid: String,
    val name: String?,
    val phases: List<Phase>?,
    val cardUrl: String?,
    val datePublished: String?,
    val duration: Int?,
    val isFavourite: Boolean
)

data class Phase(
    val uuid: String?,
    val name: String?,
    val iconUrl: String?
)