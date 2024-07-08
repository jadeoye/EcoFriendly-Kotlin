package com.example.initial.viewmodels.give

import androidx.lifecycle.ViewModel
import com.example.initial.persistence.entities.Category
import com.example.initial.repositories.CategoryRepository
import com.example.initial.repositories.ExchangeableRepository
import kotlinx.coroutines.runBlocking

class GiveViewModel(
    private val categoryRepository: CategoryRepository,
    private val exchangeableRepository: ExchangeableRepository
) : ViewModel() {
    suspend fun listCategories(): List<Category> {
        return categoryRepository.list()
    }

    suspend fun give(name: String, category: Category, photo: String) {
        exchangeableRepository.add(name, category, photo)

    }
}