package com.capstone.nongglenonggle.app.di

import android.content.Context
import androidx.room.Room
import com.capstone.nongglenonggle.data.local_datasource.RegionDao
import com.capstone.nongglenonggle.data.local_datasource.RegionDatabase
import com.google.android.datatransport.runtime.dagger.Module
import com.google.android.datatransport.runtime.dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object LocalDataBaseModule {

    @Provides
    fun provideRegionDao(db: RegionDatabase): RegionDao = db.regionInfoDao()

//    @Provides
//    @Singleton
//    fun provideDatabase(@ApplicationContext context: Context): RegionDatabase
//        = Room.databaseBuilder(context, RegionDatabase::class.java, "region.db")
}