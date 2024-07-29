package com.example.initial.viewmodels.give;

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.initial.repositories.CategoryRepository
import com.example.initial.repositories.ExchangeableRepository

class GiveViewModelFactory(
    private val categoryRepository: CategoryRepository,
    private val exchangeableRepository: ExchangeableRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GiveViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GiveViewModel(categoryRepository, exchangeableRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}