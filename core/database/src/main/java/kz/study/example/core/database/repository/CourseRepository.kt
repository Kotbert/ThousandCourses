package kz.study.example.core.database.repository

import kz.study.example.core.database.dao.CourseDao
import kz.study.example.core.database.mapper.toCourse
import kz.study.example.core.database.mapper.toEntity
import kz.study.example.core.network.api.CourseApi
import kz.study.example.core.network.mapper.toCourse
import kz.study.example.core.network.model.Course
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CourseRepository(
    private val api: CourseApi,
    private val dao: CourseDao
) {
    val favoriteIds: Flow<Set<Int>> = dao.getAll().map { list -> list.map { it.id }.toSet() }

    suspend fun fetchCourses(): List<Course> {
        val courses = api.getCourses().courses.map { it.toCourse() }
        courses.filter { it.hasLike }.forEach { course ->
            if (dao.getById(course.id) == null) {
                dao.insert(course.toEntity())
            }
        }
        return courses
    }

    suspend fun addFavorite(course: Course) {
        dao.insert(course.toEntity())
    }

    suspend fun removeFavorite(course: Course) {
        dao.delete(course.toEntity())
    }
}
