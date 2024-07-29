package com.example.initial.persistence.interfaces

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.initial.persistence.entities.Category
import kotlinx.coroutines.flow.Flow

@Dao
interface ICategory {
    @Query("SELECT * FROM categories WHERE isDeleted = 0")
    suspend fun list() : List<Category>

    @Insert
    suspend fun add(category: Category)
}