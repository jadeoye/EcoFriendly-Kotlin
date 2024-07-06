package com.example.initial.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.initial.persistence.entities.Category
import com.example.initial.repositories.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class CategoryViewModel(private val categoryRepository: CategoryRepository) : ViewModel() {
    suspend fun list(): Flow<List<Category>> {
        return categoryRepository.list()
    }
}
