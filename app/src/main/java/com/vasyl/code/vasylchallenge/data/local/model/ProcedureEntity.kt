package com.vasyl.code.vasylchallenge.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "procedure")
data class ProcedureEntity(
    @PrimaryKey
    val uuid: String,
    val iconUrl: String?,
    val name: String?,
    val phases: List<String>?,
    val isFavorite: Boolean = false
)