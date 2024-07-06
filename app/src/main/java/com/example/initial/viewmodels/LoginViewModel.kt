package com.example.initial.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.initial.repositories.UserRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: UserRepository) : ViewModel() {
    private val _isValidUser = MutableLiveData<Boolean>()
    val isValidUser: LiveData<Boolean> get() = _isValidUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _loginErrorMessage = MutableLiveData<String?>()
    val loginErrorMessage: LiveData<String?> get() = _loginErrorMessage

    fun authenticate(email: String, password: String) {
        viewModelScope.launch {
            _isLoading.value  = true
            _loginErrorMessage.value = null
            _isValidUser.value = repository.authenticate(email, password)
            _isLoading.value = false

            if(_isValidUser.value == false)
                _loginErrorMessage.value = "Incorrect Credentials"
        }
    }
}
