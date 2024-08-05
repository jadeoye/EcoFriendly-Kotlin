// LeaderboardViewModel.kt

package com.example.initial.viewmodels.leaderboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.initial.persistence.entities.Leaderboard
import com.example.initial.repositories.LeaderboardRepository
import kotlinx.coroutines.launch

class LeaderboardViewModel(private val leaderboardRepository: LeaderboardRepository) : ViewModel() {
    private val _leaderboard = MutableLiveData<List<Leaderboard>>()
    val leaderboard: LiveData<List<Leaderboard>> get() = _leaderboard

    fun fetchLeaderboard(period: Long, currentUser: Leaderboard) {
        viewModelScope.launch {
            val defaultUsers = listOf(
                Leaderboard(name = "Sodiq", points = 250, createdOn = System.currentTimeMillis()),
                Leaderboard(name = "Jeremiah", points = 200, createdOn = System.currentTimeMillis()),
                Leaderboard(name = "Angeline", points = 180, createdOn = System.currentTimeMillis())
            )

            // Combine default users with current leaderboard data and the logged-in user
            val leaderboardData = leaderboardRepository.listTopDonors(period)
            val updatedLeaderboard = defaultUsers + leaderboardData + currentUser

            _leaderboard.value = updatedLeaderboard
        }
    }
}
