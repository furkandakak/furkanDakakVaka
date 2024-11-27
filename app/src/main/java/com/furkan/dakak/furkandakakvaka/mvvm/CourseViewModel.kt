package com.furkan.dakak.furkandakakvaka.mvvm

import androidx.lifecycle.*
import com.furkan.dakak.furkandakakvaka.db.Course
import kotlinx.coroutines.launch

class CourseViewModel(private val repository: CourseRepository) : ViewModel() {

    private val _userId = MutableLiveData<String>()
    val userCourses: LiveData<List<Course>> = _userId.switchMap { userId ->
        repository.getUserCourses(userId)
    }

    fun setUserId(userId: String) {
        _userId.value = userId
    }

    fun addCourse(course: Course) {
        viewModelScope.launch {
            repository.addCourse(course)
        }
    }
}
