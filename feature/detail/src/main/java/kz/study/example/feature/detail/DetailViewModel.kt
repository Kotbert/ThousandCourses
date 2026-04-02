package kz.study.example.feature.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kz.study.example.core.database.repository.CourseRepository
import kz.study.example.core.network.model.Course
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

data class DetailUiState(
    val course: Course? = null,
    val isFavorite: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null
)

class DetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: CourseRepository
) : ViewModel() {

    private val courseId: Int = checkNotNull(savedStateHandle["courseId"])

    private val _state = MutableStateFlow(DetailUiState())
    val state: StateFlow<DetailUiState> = _state.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) { loadCourse() }
        viewModelScope.launch(Dispatchers.IO) {
            repository.favoriteIds.collect { ids ->
                _state.update { it.copy(isFavorite = courseId in ids) }
            }
        }
    }

    private suspend fun loadCourse() {
        _state.update { it.copy(isLoading = true) }
        runCatching { repository.fetchCourses() }
            .onSuccess { courses ->
                _state.update { it.copy(course = courses.find { c -> c.id == courseId }, isLoading = false) }
            }
            .onFailure { e ->
                _state.update { it.copy(isLoading = false, error = e.message) }
            }
    }

    fun toggleFavorite() {
        val course = _state.value.course ?: return
        viewModelScope.launch(Dispatchers.IO) {
            if (_state.value.isFavorite) repository.removeFavorite(course)
            else repository.addFavorite(course)
        }
    }
}
