package com.example.initial.repositories

import android.util.Log
import com.example.initial.persistence.entities.User
import com.example.initial.persistence.interfaces.IUser

class UserRepository(private val iUser: IUser) {
    suspend fun authenticate(email: String, password: String): User? {
        Log.d("UserRepository", "Authenticating: $email / $password")
        val user = iUser.authenticate(email, password)
        Log.d("UserRepository", "Authenticated user: ${user?.email}")
        return user
    }

    suspend fun list(): List<User> {
        return iUser.list()
    }
}
