package com.vasyl.code.vasylchallenge.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vasyl.code.vasylchallenge.data.local.model.ProcedureEntity

@Dao
interface ProcedureDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProcedures(procedures: List<ProcedureEntity>)

    @Query("SELECT * FROM procedure")
    fun getProcedures(): List<ProcedureEntity>

    @Query("SELECT * FROM procedure WHERE isFavorite = 1")
    fun getFavoriteProcedures(): List<ProcedureEntity>

    @Query("UPDATE procedure SET isFavorite = :isFavorite WHERE  uuid = :uuid")
    suspend fun updateProcedureFavoriteStatus(uuid: String, isFavorite: Boolean)

    @Query("SELECT uuid FROM procedure WHERE isFavorite = 1")
    suspend fun getFavoriteUuids(): List<String>
}