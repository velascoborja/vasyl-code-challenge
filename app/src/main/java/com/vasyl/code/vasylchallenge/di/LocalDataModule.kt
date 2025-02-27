package com.vasyl.code.vasylchallenge.di

import android.content.Context
import androidx.room.Room
import com.vasyl.code.vasylchallenge.data.local.MyAppDatabase
import com.vasyl.code.vasylchallenge.data.local.dao.ProcedureDao
import com.vasyl.code.vasylchallenge.data.local.dao.ProcedureDetailsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class LocalDataModule {

    @Provides
    @Singleton
    fun providesDatabase(
        @ApplicationContext appContext: Context
    ): MyAppDatabase {
        return Room.databaseBuilder(
            appContext,
            MyAppDatabase::class.java,
            "my_app_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun providesProcedureDao(database: MyAppDatabase): ProcedureDao {
        return database.procedureDao()
    }

    @Provides
    fun providesProcedureDetailsDao(database: MyAppDatabase): ProcedureDetailsDao {
        return database.procedureDetailsDao()
    }
}