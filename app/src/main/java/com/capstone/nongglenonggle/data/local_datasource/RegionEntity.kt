package com.capstone.nongglenonggle.data.local_datasource

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "region")
data class RegionEntity (
    @PrimaryKey val id: String,
    @ColumnInfo(name = "categoryTitle") val parentRegion: String
)