package com.capstone.nongglenonggle.app.di

import android.content.Context
import androidx.room.Room
import com.capstone.nongglenonggle.data.local_datasource.RegionDao
import com.capstone.nongglenonggle.data.local_datasource.RegionDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDataBaseModule {

    @Provides
    @Singleton
    fun provideRegionDatabase(
        @ApplicationContext context: Context
    ): RegionDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            RegionDatabase::class.java,
            "region_database"
        )
            .addMigrations(RegionDatabase.MIGRATION_1_2)
            .build()
    }

    @Provides
    @Singleton
    fun provideRegionDao(database: RegionDatabase): RegionDao {
        return database.regionInfoDao()
    }
}
