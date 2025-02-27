package com.vasyl.code.vasylchallenge.data.local.model


import androidx.room.*

@Entity(tableName = "procedure_details")
data class ProcedureDetailEntity(
    @PrimaryKey
    val uuid: String,
    val name: String?,
    val phases: List<PhaseEntity>?,
    val cardUrl: String?,
    val datePublished: String?,
    val duration: Int?
)

data class PhaseEntity(
    val uuid: String?,
    val name: String?,
    val iconUrl: String?
)