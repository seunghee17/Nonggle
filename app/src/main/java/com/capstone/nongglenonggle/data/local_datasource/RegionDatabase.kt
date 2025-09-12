package com.capstone.nongglenonggle.data.local_datasource

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [RegionEntity::class], version = 1, exportSchema = false)
abstract class RegionDatabase: RoomDatabase() {
}