package kz.study.example.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kz.study.example.core.database.entity.CourseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CourseDao {
    @Query("SELECT * FROM favorite_courses")
    fun getAll(): Flow<List<CourseEntity>>

    @Query("SELECT * FROM favorite_courses WHERE id = :id")
    suspend fun getById(id: Int): CourseEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(course: CourseEntity)

    @Delete
    suspend fun delete(course: CourseEntity)
}
