package com.capstone.nongglenonggle.data.local_datasource

import androidx.room.Entity
import androidx.room.InvalidationTracker
import androidx.room.RoomDatabase

@Entity(tableName = "region")
data class RegionEntity: RoomDatabase() {
    override fun clearAllTables() {
        TODO("Not yet implemented")
    }

    override fun createInvalidationTracker(): InvalidationTracker {
        TODO("Not yet implemented")
    }
}