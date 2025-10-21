package com.capstone.nongglenonggle.data.local_datasource

import androidx.room.Entity
import androidx.room.PrimaryKey

import androidx.room.*

/**
 * 상위 지역 엔티티 (예: 서울, 강원, 경기 등)
 */
@Entity(tableName = "region")
data class RegionEntity(
    @PrimaryKey(autoGenerate = true) val regionId: Long = 0,
    val name: String
)

/**
 * 하위 지역 엔티티 (예: 강남구, 춘천시 등)
 * RegionEntity와 1:N 관계
 */
@Entity(
    tableName = "district",
    foreignKeys = [
        ForeignKey(
            entity = RegionEntity::class,
            parentColumns = ["regionId"],
            childColumns = ["regionOwnerId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("regionOwnerId")]
)
data class DistrictEntity(
    @PrimaryKey(autoGenerate = true) val districtId: Long = 0,
    val name: String,
    val regionOwnerId: Long
)


