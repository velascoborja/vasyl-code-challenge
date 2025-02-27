package com.vasyl.code.vasylchallenge.di

import com.vasyl.code.vasylchallenge.data.local.dao.ProcedureDao
import com.vasyl.code.vasylchallenge.data.local.dao.ProcedureDetailsDao
import com.vasyl.code.vasylchallenge.data.remote.service.ProcedureService
import com.vasyl.code.vasylchallenge.data.repository.procedure.ProcedureRepositoryImpl
import com.vasyl.code.vasylchallenge.domain.repository.procedure.ProcedureRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideProceduresRepo(
        procedureService: ProcedureService,
        procedureDao: ProcedureDao,
        procedureDetailsDao: ProcedureDetailsDao
    ): ProcedureRepository {
        return ProcedureRepositoryImpl(procedureService, procedureDao, procedureDetailsDao)
    }
}