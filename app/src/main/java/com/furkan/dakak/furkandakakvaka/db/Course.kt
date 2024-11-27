package com.furkan.dakak.furkandakakvaka.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "courses")
data class Course(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: String,
    val courseUrl: String
)
