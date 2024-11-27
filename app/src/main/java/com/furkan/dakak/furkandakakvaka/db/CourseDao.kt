package com.furkan.dakak.furkandakakvaka.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CourseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCourse(course: Course) // Kurs eklemek için

    @Query("SELECT * FROM courses WHERE userId = :userId")
    fun getCoursesForUser(userId: String): LiveData<List<Course>> // Kullanıcıya ait kursları almak için
}

