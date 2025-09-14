package com.capstone.nongglenonggle.app.di

import android.content.Context
import androidx.room.Room
import com.google.android.datatransport.runtime.dagger.Module
import com.google.android.datatransport.runtime.dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
//https://e0df02f3-185a-490b-bd1e-1019fbe48cab.mock.pstmn.io
//@InstallIn(SingletonComponent::class)
//@Module
//object LocalDataBaseModule {
//
//    @Provides
//    fun provideRegionDao(db: RegionDatabase): RegionDao = db.regionInfoDao()
//
//    @Provides
//    @Singleton
//    fun provideDatabase(@ApplicationContext context: Context): RegionDatabase
//        = Room.databaseBuilder(context, RegionDatabase::class.java, "region.db")
//}