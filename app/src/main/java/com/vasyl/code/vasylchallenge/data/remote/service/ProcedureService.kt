package com.vasyl.code.vasylchallenge.data.remote.service

import com.vasyl.code.vasylchallenge.data.remote.model.procedure.ProcedureDto
import com.vasyl.code.vasylchallenge.data.remote.model.procedure.ProcedureDetailsDto
import retrofit2.http.GET
import retrofit2.http.Path

interface ProcedureService {

    @GET("procedures")
    suspend fun getProcedures(): List<ProcedureDto>

    @GET("procedures/{uuid}")
    suspend fun getProcedureDetails(
        @Path("uuid") procedureUuid: String
    ): ProcedureDetailsDto
}