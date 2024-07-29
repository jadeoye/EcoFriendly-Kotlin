package com.example.initial.viewmodels.leaderboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.initial.repositories.LeaderboardRepository

class LeaderboardViewModelFactory(private val leaderboardRepository: LeaderboardRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LeaderboardViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LeaderboardViewModel(leaderboardRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
