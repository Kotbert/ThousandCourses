package kz.study.example.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import kz.study.example.core.database.dao.CourseDao
import kz.study.example.core.database.entity.CourseEntity

@Database(entities = [CourseEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun courseDao(): CourseDao
}
