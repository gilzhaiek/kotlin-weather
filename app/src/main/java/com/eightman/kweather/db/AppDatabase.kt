package com.eightman.kweather.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

import com.eightman.kweather.KWeatherApplication
import com.eightman.kweather.db.dao.LocationDao
import com.eightman.kweather.db.entities.LocationEntity

@Database(
    version = 1,
    entities = [LocationEntity::class]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun locationDao(): LocationDao

    companion object {
        private const val DATABASE_NAME = "app_database"
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(): AppDatabase {
            if (INSTANCE == null) {
                rebuild()
            }
            return INSTANCE!!
        }

        fun rebuild() {
            synchronized(AppDatabase::class) {
                INSTANCE = Room.databaseBuilder(
                    KWeatherApplication.instance,
                    AppDatabase::class.java,
                    DATABASE_NAME
                ).allowMainThreadQueries()
                    .build()
            }
        }

        fun clearDatabase() {
            getDatabase().clearAllTables()
        }
    }
}