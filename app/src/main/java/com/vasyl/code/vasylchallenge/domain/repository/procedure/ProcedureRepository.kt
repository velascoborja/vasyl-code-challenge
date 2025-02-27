package com.vasyl.code.vasylchallenge.domain.repository.procedure

import com.vasyl.code.vasylchallenge.domain.model.Procedure
import com.vasyl.code.vasylchallenge.domain.model.ProcedureDetails

interface ProcedureRepository {

    suspend fun getProcedures(): List<Procedure>

    suspend fun getProcedureDetails(procedureUuid: String): ProcedureDetails

    suspend fun toggleProcedureFavoriteStatus(procedureUuid: String, isFavorite: Boolean)

    suspend fun getFavouriteProcedures(): List<Procedure>
}