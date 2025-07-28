package com.padieer.asesoriapp

import android.util.Log
import androidx.compose.ui.util.fastCbrt
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.padieer.asesoriapp.data.token.LoginRepository
import com.padieer.asesoriapp.data.viewModelFactory
import com.padieer.asesoriapp.domain.error.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class MainAppInfo(
    val isReady: Boolean = false,
    val isLoggedIn: Boolean = false,
)

class MainViewModel(
    private val loginRepository: LoginRepository
): ViewModel() {

    private val _state = MutableStateFlow(MainAppInfo())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val loginResult = loginRepository.getLoggedInUser()
            if (loginResult is Result.Success) {
                _state.update { it.copy(
                    isLoggedIn = true,
                ) }
            }
            _state.update { it.copy(
                isReady = true
            ) }
        }
    }

    companion object {
        fun Factory() = viewModelFactory {
            MainViewModel(
                loginRepository = App.appModule.loginRepository
            )
        }
    }
}