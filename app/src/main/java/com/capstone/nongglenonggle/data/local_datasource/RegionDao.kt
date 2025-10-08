package com.capstone.nongglenonggle.data.local_datasource

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.capstone.nongglenonggle.data.model.worker.RegionListModel

/**
 * DAO
 */
@Dao
interface RegionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRegion(region: RegionEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDistricts(districts: List<DistrictEntity>)

    @Transaction
    fun insertRegionsWithDistricts(subRegions: List<RegionListModel>) {
        subRegions.forEach { subRegion ->
            val regionId = insertRegion(RegionEntity(name = subRegion.name))
            val districts = subRegion.districts.map { district ->
                DistrictEntity(
                    name = district,
                    regionOwnerId = regionId
                )
            }
            insertDistricts(districts)
        }
    }

    @Query("SELECT DISTINCT name FROM region")
    fun getAllRegion(): List<String>


    @Query("""
        SELECT * FROM district
        WHERE regionOwnerId = (
            SELECT regionId FROM region
            WHERE name = :regionName
        )
        ORDER BY name
    """)
    fun getDistrictsByRegion(regionName: String): List<DistrictEntity>
}
