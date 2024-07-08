package com.example.initial.persistence.interfaces

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.initial.persistence.entities.User

@Dao
interface IUser {
    @Query("SELECT * FROM users WHERE email = :email AND password = :password AND isDeleted = 0")
    suspend fun authenticate(email: String, password: String): User?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun add(user: User)

    @Query("SELECT * FROM users")
    suspend fun list(): List<User>
}
