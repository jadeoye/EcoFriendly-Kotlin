package com.example.initial.viewmodels.give

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import com.example.initial.helpers.sendNotificationToWeb
import com.example.initial.helpers.signalr_notification_label
import com.example.initial.persistence.entities.Category
import com.example.initial.repositories.CategoryRepository
import com.example.initial.repositories.ExchangeableRepository
import com.example.initial.services.WebNotificationService

class GiveViewModel(
    private val categoryRepository: CategoryRepository,
    private val exchangeableRepository: ExchangeableRepository
) : ViewModel() {
    suspend fun listCategories(): List<Category> {
        return categoryRepository.list()
    }

    suspend fun give(context: Context, name: String, category: Category, photo: String) {
        exchangeableRepository.add(name, category, photo)
        sendNotificationToWeb(context, "Donation|1.0")
    }
}