package com.capstone.nongglenonggle.data.local_datasource

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [RegionEntity::class, DistrictEntity::class], version = 1, exportSchema = false)
abstract class RegionDatabase: RoomDatabase() {
    abstract fun regionInfoDao(): RegionDao

    companion object {
        private var INSTANCE: RegionDatabase? = null
        private val MIGRATION_1_2 = object: Migration(1,2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE region ADD COLUMN description TEXT")
            }
        }

        @Synchronized
        fun getDatabase(context: Context) : RegionDatabase {
            if(INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    RegionDatabase::class.java,
                    "region_database"
                ).addMigrations(MIGRATION_1_2).build()
            }
            return INSTANCE as RegionDatabase
        }
    }
}