package com.furkan.dakak.furkandakakvaka.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Course::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun courseDao(): CourseDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "learnconnect_db"
                ).build().also { instance = it }
            }
        }
    }
}
