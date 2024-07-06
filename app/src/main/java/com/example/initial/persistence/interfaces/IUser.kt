package com.example.initial.persistence.interfaces

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.initial.persistence.entities.User

@Dao
interface IUser {
    @Query("SELECT EXISTS(SELECT 1 FROM users WHERE email = :email AND password = :password)")
    suspend fun authenticate(email: String, password: String): Boolean

    @Insert
    suspend fun add(user: User)
}
