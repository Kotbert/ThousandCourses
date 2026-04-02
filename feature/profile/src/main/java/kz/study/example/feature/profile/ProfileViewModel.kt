package kz.study.example.feature.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kz.study.example.core.database.dao.CourseDao
import kz.study.example.core.database.mapper.toCourse
import kz.study.example.core.network.model.Course
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(private val dao: CourseDao) : ViewModel() {

    private val _courses = MutableStateFlow<List<Course>>(emptyList())
    val courses = _courses.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            dao.getAll().collect { list ->
                _courses.value = list.map { it.toCourse() }
            }
        }
    }
}
