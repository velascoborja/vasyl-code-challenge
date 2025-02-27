package com.vasyl.code.vasylchallenge.data.repository.procedure

import com.vasyl.code.vasylchallenge.data.converters.procedure.toDomain
import com.vasyl.code.vasylchallenge.data.converters.procedure.toEntity
import com.vasyl.code.vasylchallenge.data.local.dao.ProcedureDao
import com.vasyl.code.vasylchallenge.data.local.dao.ProcedureDetailsDao
import com.vasyl.code.vasylchallenge.data.remote.service.ProcedureService
import com.vasyl.code.vasylchallenge.domain.model.Procedure
import com.vasyl.code.vasylchallenge.domain.model.ProcedureDetails
import com.vasyl.code.vasylchallenge.domain.repository.procedure.ProcedureRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProcedureRepositoryImpl @Inject constructor(
    private val procedureService: ProcedureService,
    private val procedureDao: ProcedureDao,
    private val procedureDetailsDao: ProcedureDetailsDao
) : ProcedureRepository {

    override suspend fun getProcedures(): List<Procedure> {
        return withContext(Dispatchers.IO) {
            try {
                val remoteProcedures = procedureService.getProcedures()
                val existingFavorites = procedureDao.getFavoriteUuids()

                val updatedProcedures = remoteProcedures.map {
                    it.toEntity(
                        existingFavorites.contains(it.uuid)
                    )
                }
                procedureDao.insertProcedures(updatedProcedures)
                updatedProcedures.map { it.toDomain() }
            } catch (ex: Exception) {
                // TODO: Print log
                procedureDao.getProcedures().map { it.toDomain() }.ifEmpty { throw ex }
            }
        }
    }

    override suspend fun getProcedureDetails(procedureUuid: String): ProcedureDetails {
        return withContext(Dispatchers.IO) {
            val existingFavorites = procedureDao.getFavoriteUuids()
            try {
                val remoteProcedureDetails = procedureService.getProcedureDetails(procedureUuid)
                val updatedProcedureDetails = remoteProcedureDetails.toEntity()
                procedureDetailsDao.insertProcedureDetails(updatedProcedureDetails)
                updatedProcedureDetails.toDomain(existingFavorites.contains(procedureUuid))
            } catch (ex: Exception) {
                // TODO: Print log
                procedureDetailsDao.getProcedureDetailsByUuid(procedureUuid)?.toDomain(existingFavorites.contains(procedureUuid)) ?: throw ex
            }
        }
    }

    override suspend fun toggleProcedureFavoriteStatus(procedureUuid: String, isFavorite: Boolean) {
        withContext(Dispatchers.IO) {
            procedureDao.updateProcedureFavoriteStatus(uuid = procedureUuid, isFavorite = isFavorite)
        }
    }

    override suspend fun getFavouriteProcedures(): List<Procedure> {
        return withContext(Dispatchers.IO) { procedureDao.getFavoriteProcedures().map { it.toDomain() } }
    }
}