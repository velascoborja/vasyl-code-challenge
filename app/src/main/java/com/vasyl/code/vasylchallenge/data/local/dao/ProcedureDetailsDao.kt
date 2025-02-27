package com.vasyl.code.vasylchallenge.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vasyl.code.vasylchallenge.data.local.model.ProcedureDetailEntity

@Dao
interface ProcedureDetailsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProcedureDetails(details: ProcedureDetailEntity)

    @Query("SELECT * FROM procedure_details  WHERE uuid = :uuid LIMIT 1")
    fun getProcedureDetailsByUuid(uuid: String): ProcedureDetailEntity?
}