package com.example.initial.viewmodels.category

import androidx.lifecycle.ViewModel
import com.example.initial.persistence.entities.Category
import com.example.initial.repositories.CategoryRepository

class CategoryViewModel(private val categoryRepository: CategoryRepository) : ViewModel() {
    suspend fun list(): List<Category> {
        return categoryRepository.list()
    }
}
