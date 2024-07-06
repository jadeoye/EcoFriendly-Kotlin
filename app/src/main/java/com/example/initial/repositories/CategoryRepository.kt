package com.example.initial.repositories

import com.example.initial.persistence.entities.Category
import com.example.initial.persistence.interfaces.ICategory
import kotlinx.coroutines.flow.Flow

class CategoryRepository(private val categoryInterface: ICategory) {
    suspend fun list() : Flow<List<Category>> {
        return categoryInterface.list()
    }
}
