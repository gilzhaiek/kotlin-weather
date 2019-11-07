package com.eightman.kweather.db.entities

import androidx.room.Entity
import com.eightman.kweather.network.getIconRes

@Entity(tableName = "location", primaryKeys = ["timestamp"])
open class LocationEntity(
    val timestamp: Int,
    val latitude: Double,
    val longitude: Double
)