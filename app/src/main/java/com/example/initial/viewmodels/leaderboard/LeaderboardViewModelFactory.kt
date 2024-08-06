package com.example.initial.viewmodels.leaderboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.initial.repositories.LeaderboardRepository
import com.example.initial.viewmodels.helpers.user.sessions.UserSessionViewModel
import com.example.initial.viewmodels.vouchers.VouchersViewModel

data class LeaderboardViewModelFactory(private val repository: LeaderboardRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LeaderboardViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LeaderboardViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}