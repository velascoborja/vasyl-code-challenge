package com.vasyl.code.vasylchallenge.domain.model

data class Procedure(
    val uuid: String,
    val iconUrl: String?,
    val name: String?,
    val phases: List<String>?,
    val isFavorite: Boolean = false
)
