package com.example.initial.viewmodels.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.initial.persistence.entities.User
import com.example.initial.repositories.UserRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: UserRepository) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _loginErrorMessage = MutableLiveData<String?>()
    val loginErrorMessage: LiveData<String?> get() = _loginErrorMessage

    fun authenticate(email: String, password: String, callback: (User?) -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            _loginErrorMessage.value = null
            val user = repository.authenticate(email, password)
            _isLoading.value = false

            if (user == null)
                _loginErrorMessage.value = "Incorrect Credentials"

            callback(user)
        }
    }

    fun list() {
        viewModelScope.launch {
            val users = repository.list()
            users.forEach { user ->
                Log.d("LoginViewModel", "User: ${user.firstName} ${user.lastName} - ${user.email}")
            }
        }
    }
}
