package com.example.initial.viewmodels.helpers.user.sessions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.initial.persistence.entities.User

class UserSessionViewModel : ViewModel() {
    private val _user = MutableLiveData<User>()
    val user: LiveData<User> get() = _user

    fun login(user: User) {
        _user.value = user
    }

    fun logout() {
        _user.value = null
    }
}