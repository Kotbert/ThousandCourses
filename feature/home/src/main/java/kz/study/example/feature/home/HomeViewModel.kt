package kz.study.example.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kz.study.example.core.network.model.Course
import kz.study.example.core.database.repository.CourseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

data class HomeUiState(
    val courses: List<Course> = emptyList(),
    val favoriteIds: Set<Int> = emptySet(),
    val isLoading: Boolean = false,
    val isSorted: Boolean = false,
    val error: String? = null
)

class HomeViewModel(private val repository: CourseRepository) : ViewModel() {

    private val _state = MutableStateFlow(HomeUiState())
    val state: StateFlow<HomeUiState> = _state.asStateFlow()

    private var originalCourses: List<Course> = emptyList()

    init {
        observeFavorites()
        loadCourses()
    }

    private fun observeFavorites() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.favoriteIds.collect { ids ->
                _state.update { it.copy(favoriteIds = ids) }
            }
        }
    }

    private fun loadCourses() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isLoading = true, error = null) }
            runCatching { repository.fetchCourses() }
                .onSuccess { courses ->
                    originalCourses = courses
                    _state.update { it.copy(courses = courses, isLoading = false) }
                }
                .onFailure { e ->
                    _state.update { it.copy(isLoading = false, error = e.message) }
                }
        }
    }

    fun toggleSort() {
        val sorted = !_state.value.isSorted
        val list = if (sorted) {
            originalCourses.sortedByDescending { it.publishDate }
        } else {
            originalCourses
        }
        _state.update { it.copy(courses = list, isSorted = sorted) }
    }

    fun toggleFavorite(course: Course) {
        viewModelScope.launch(Dispatchers.IO) {
            if (course.id in _state.value.favoriteIds) {
                repository.removeFavorite(course)
            } else {
                repository.addFavorite(course)
            }
        }
    }
}
