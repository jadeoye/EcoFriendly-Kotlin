package com.example.initial.repositories

import com.example.initial.persistence.interfaces.IUser

class UserRepository (private val userInterface: IUser) {
    suspend fun authenticate(email: String, password: String) : Boolean {
        return userInterface.authenticate(email, password)
    }
}