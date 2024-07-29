package com.example.initial.repositories

import com.example.initial.persistence.entities.Leaderboard
import com.example.initial.persistence.interfaces.ILeaderboard

class LeaderboardRepository(private val leaderboardInterface: ILeaderboard) {
    suspend fun listTopDonors(period: Long): List<Leaderboard> {
        return leaderboardInterface.listTopDonors(period)
    }

    suspend fun insert(leaderboard: Leaderboard) {
        leaderboardInterface.insert(leaderboard)
    }
}
