package com.example.initial.viewmodels.leaderboard

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.initial.persistence.entities.LeaderboardEntry
import com.example.initial.repositories.LeaderboardRepository
import kotlinx.coroutines.launch

class LeaderboardViewModel(
    private val repository: LeaderboardRepository,
) : ViewModel() {
    suspend fun list(): List<LeaderboardEntry> {
        return repository.list()
    }

}