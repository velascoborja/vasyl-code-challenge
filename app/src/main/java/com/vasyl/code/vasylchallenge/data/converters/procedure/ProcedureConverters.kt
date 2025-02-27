package com.vasyl.code.vasylchallenge.data.converters.procedure

import com.vasyl.code.vasylchallenge.data.local.model.PhaseEntity
import com.vasyl.code.vasylchallenge.data.local.model.ProcedureDetailEntity
import com.vasyl.code.vasylchallenge.data.local.model.ProcedureEntity
import com.vasyl.code.vasylchallenge.data.remote.model.procedure.PhaseDto
import com.vasyl.code.vasylchallenge.data.remote.model.procedure.ProcedureDetailsDto
import com.vasyl.code.vasylchallenge.data.remote.model.procedure.ProcedureDto
import com.vasyl.code.vasylchallenge.domain.model.Phase
import com.vasyl.code.vasylchallenge.domain.model.Procedure
import com.vasyl.code.vasylchallenge.domain.model.ProcedureDetails

fun ProcedureEntity.toDomain() = Procedure(
    uuid = uuid,
    iconUrl = iconUrl,
    name = name,
    phases = phases,
    isFavorite = isFavorite
)

fun ProcedureDto.toEntity(isFavourite: Boolean) = ProcedureEntity(
    uuid = uuid,
    iconUrl = icon?.url,
    name = name,
    phases = phases,
    isFavorite = isFavourite
)

fun ProcedureDto.toDomain(isFavourite: Boolean) = Procedure(
    uuid = uuid,
    iconUrl = icon?.url,
    name = name,
    phases = phases,
    isFavorite = isFavourite
)

fun ProcedureDetailEntity.toDomain(isFavourite: Boolean) = ProcedureDetails(
    uuid = uuid, name = name,
    phases = phases?.map { it.toDomain() },
    cardUrl = cardUrl,
    datePublished = datePublished,
    duration = duration,
    isFavourite = isFavourite
)

fun PhaseEntity.toDomain() = Phase(
    uuid = uuid,
    name = name,
    iconUrl = iconUrl
)

fun ProcedureDetailsDto.toEntity() = ProcedureDetailEntity(
    uuid = uuid,
    name = name,
    phases = phases?.map { it.toEntity() },
    cardUrl = card?.url,
    datePublished = datePublished,
    duration = duration
)

fun ProcedureDetailsDto.toDomain(isFavourite: Boolean) = ProcedureDetails(
    uuid = uuid,
    name = name,
    phases = phases?.map { it.toDomain() },
    cardUrl = card?.url,
    datePublished = datePublished,
    duration = duration,
    isFavourite = isFavourite
)

fun PhaseDto.toDomain() = Phase(
    uuid = uuid,
    name = name,
    iconUrl = icon?.url
)

fun PhaseDto.toEntity() = PhaseEntity(
    uuid = uuid,
    name = name,
    iconUrl = icon?.url
)

