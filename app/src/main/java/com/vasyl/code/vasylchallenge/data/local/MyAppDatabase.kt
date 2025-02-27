package com.vasyl.code.vasylchallenge.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.vasyl.code.vasylchallenge.data.local.converters.PhaseEntityListConverter
import com.vasyl.code.vasylchallenge.data.local.converters.StringListConverter
import com.vasyl.code.vasylchallenge.data.local.dao.ProcedureDao
import com.vasyl.code.vasylchallenge.data.local.dao.ProcedureDetailsDao
import com.vasyl.code.vasylchallenge.data.local.model.ProcedureDetailEntity
import com.vasyl.code.vasylchallenge.data.local.model.ProcedureEntity


@Database(
    entities = [
        ProcedureEntity::class,
        ProcedureDetailEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters( // TODO: Add
    StringListConverter::class,
    PhaseEntityListConverter::class
)
abstract class MyAppDatabase : RoomDatabase() {
    abstract fun procedureDao(): ProcedureDao
    abstract fun procedureDetailsDao(): ProcedureDetailsDao
}