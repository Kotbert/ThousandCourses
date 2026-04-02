package kz.study.example.feature.login

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class LoginState(
    val email: String = "asd@asd.asd",
    val password: String = "",
    val isLoginEnabled: Boolean = false
)

class LoginViewModel : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state.asStateFlow()

    fun onEmailChanged(value: String) {
        val filtered = value.filter { it.isLetterOrDigit() || it in "@._-+" }
            .filter { it.code < 128 }
        _state.update { it.copy(email = filtered, isLoginEnabled = isValid(filtered, it.password)) }
    }

    fun onPasswordChanged(value: String) {
        _state.update { it.copy(password = value, isLoginEnabled = isValid(it.email, value)) }
    }

    private fun isValid(email: String, password: String): Boolean {
        val emailRegex = Regex("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
        return emailRegex.matches(email) && password.isNotEmpty()
    }
}
