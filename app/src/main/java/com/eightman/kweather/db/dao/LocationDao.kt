package com.eightman.kweather.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.eightman.kweather.db.entities.LocationEntity

@Dao
interface LocationDao {
    @Transaction
    @Query("select * from location where timestamp >= :earliest")
    fun getLocationHistory(earliest: Int = 0): LiveData<List<LocationEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(locationEntity: LocationEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(locationEntityList: List<LocationEntity>)

    @Query("delete from location where timestamp < :timestamp")
    fun deleteBefore(timestamp: Int)
}