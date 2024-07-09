package com.example.initial.viewmodels.donations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.initial.repositories.DonationsRepository
import com.example.initial.viewmodels.helpers.user.sessions.UserSessionViewModel

class DonationsViewModelFactory(private val donationsRepository: DonationsRepository, private val userSessionViewModel: UserSessionViewModel) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DonationsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DonationsViewModel(donationsRepository, userSessionViewModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}