package com.furkan.dakak.furkandakakvaka.mvvm

import com.furkan.dakak.furkandakakvaka.db.Course
import com.furkan.dakak.furkandakakvaka.db.CourseDao

class CourseRepository(private val courseDao: CourseDao) {
    suspend fun addCourse(course: Course) = courseDao.insertCourse(course)
    fun getUserCourses(userId: String) = courseDao.getCoursesForUser(userId)
}
