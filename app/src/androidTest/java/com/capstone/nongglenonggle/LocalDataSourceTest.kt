package com.capstone.nongglenonggle

import android.content.Context
import androidx.room.Room
import com.capstone.nongglenonggle.data.local_datasource.RegionDao
import com.capstone.nongglenonggle.data.local_datasource.RegionDatabase
import org.junit.Before
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.capstone.nongglenonggle.data.model.worker.RegionListModel
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Test
import junit.framework.TestCase.assertEquals
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LocalDataSourceTest {
    val context = ApplicationProvider.getApplicationContext<Context>()
    private lateinit var database: RegionDatabase
    private lateinit var regionDao: RegionDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            context,
            RegionDatabase::class.java
        ).allowMainThreadQueries().build()
        regionDao = database.regionInfoDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun getAllRegionTest() = runBlocking {
        //given nothing
        val testRegionList = listOf(RegionListModel("전체", listOf("서울특별시", "부산광역시", "제주시")), RegionListModel("서울", listOf("광진구", "성동구", "강남구")))
        //when
        regionDao.insertRegionsWithDistricts(testRegionList)
        val regions = regionDao.getAllRegion()

        // then
        assertEquals(2, regions.size)
        assertEquals("전체", regions.first())
    }

    @Test
    fun getSubRegionTest() = runBlocking {
        //given
        val testRegionList = listOf(RegionListModel("전체", listOf("서울특별시", "부산광역시", "제주시")), RegionListModel("서울", listOf("광진구", "성동구", "강남구")))

        //when
        regionDao.insertRegionsWithDistricts(testRegionList)
        val subRegions = regionDao.getDistrictsByRegion("서울")

        //then
        assertEquals(3, subRegions.size)
    }

}