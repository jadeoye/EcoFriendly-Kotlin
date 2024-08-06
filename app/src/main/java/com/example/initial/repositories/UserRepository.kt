package com.example.initial.repositories

import com.example.initial.persistence.entities.User
import com.example.initial.persistence.interfaces.IUser

class UserRepository(private val userInterface: IUser) {
    suspend fun authenticate(email: String, password: String): User? {
        return userInterface.authenticate(email, password)
    }

    suspend fun list(): List<User> {
        return userInterface.list()
    }

    suspend fun get(userId: Int): User? {
        return userInterface.get(userId)
    }
}