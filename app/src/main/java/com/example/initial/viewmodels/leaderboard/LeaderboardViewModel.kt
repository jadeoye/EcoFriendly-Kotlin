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

    init {
        // Populate with default data
        val defaultLeaderboard = listOf(
            Leaderboard(name = "Alice", points = 100, createdOn = System.currentTimeMillis()),
            Leaderboard(name = "Bob", points = 90, createdOn = System.currentTimeMillis()),
            Leaderboard(name = "Charlie", points = 80, createdOn = System.currentTimeMillis()),
            Leaderboard(name = "David", points = 70, createdOn = System.currentTimeMillis()),
            Leaderboard(name = "Eve", points = 60, createdOn = System.currentTimeMillis())
        )

        // Sort by points and assign rank
        val sortedLeaderboard = defaultLeaderboard.sortedByDescending { it.points }
            .mapIndexed { index, player -> player to (index + 1) }

        _leaderboard.value = sortedLeaderboard.map { (player, rank) ->
            player.copy(name = "#$rank ${player.name}")
        }
    }

    fun fetchLeaderboard(period: Long) {
        viewModelScope.launch {
            val fetchedLeaderboard = leaderboardRepository.listTopDonors(period)

            // Sort by points and assign rank
            val sortedLeaderboard = fetchedLeaderboard.sortedByDescending { it.points }
                .mapIndexed { index, player -> player to (index + 1) }

            _leaderboard.value = sortedLeaderboard.map { (player, rank) ->
                player.copy(name = "#$rank ${player.name}")
            }
        }
    }
}
