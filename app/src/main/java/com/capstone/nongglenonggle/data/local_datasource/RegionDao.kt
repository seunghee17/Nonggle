package com.capstone.nongglenonggle.data.local_datasource

import androidx.room.Dao
import androidx.room.Query

/**
 * DAO
 */
@Dao
interface RegionDao {
    @Query("SELECT * FROM region")
    fun getAllRegion(): List<RegionEntity>

    @Query("""
        SELECT * FROM district
        WHERE regionOwnerId = :regionId
        ORDER BY name
    """)
    fun getDistrictsByRegionId(regionId: Long): List<DistrictEntity>
}
