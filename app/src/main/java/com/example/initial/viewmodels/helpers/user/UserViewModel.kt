package com.example.initial.viewmodels.helpers.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.initial.persistence.entities.User
import com.example.initial.repositories.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(private val repository: UserRepository) : ViewModel() {
    private val _user = MutableLiveData<User>()
    val userExists: LiveData<User> get() = _user

    fun authenticate(email: String, password: String) {
        viewModelScope.launch {
            _user.value = repository.authenticate(email, password)
        }
    }
}