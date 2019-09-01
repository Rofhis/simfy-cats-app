package com.rofhiwa.simfycatsapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rofhiwa.simfycatsapp.data.db.dao.CatsDao
import com.rofhiwa.simfycatsapp.data.db.entity.CatsEntity

const val DATABASE_VERSION = 1
const val DATABASE_NAME = "simfycatsapp_db"

@Database(
    entities = [
        CatsEntity::class
    ],
    version = DATABASE_VERSION,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun provideCatsDao(): CatsDao

    companion object {

        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .allowMainThreadQueries()
                .build()
        }
    }
}