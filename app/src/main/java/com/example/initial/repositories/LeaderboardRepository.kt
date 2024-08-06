package com.example.initial.repositories

import com.example.initial.persistence.entities.LeaderboardEntry
import com.example.initial.viewmodels.helpers.user.sessions.UserSessionViewModel

class LeaderboardRepository(private val userRepository: UserRepository,
                            private val walletRepository: WalletRepository,
    private val userSessionViewModel: UserSessionViewModel) {
    suspend fun list() : List<LeaderboardEntry> {
        val user = userRepository.get(userSessionViewModel.user.value!!.id)
        val walletPoints = walletRepository.getTotalBalance()

        val entries = listOf(
            LeaderboardEntry(1, "Angel Darcus", 10),
            LeaderboardEntry(2, "David Akinyemi", 20),
            LeaderboardEntry(3, "Ikechukwu Opkala", 30),
            LeaderboardEntry(4, "Sodiq Adewole", 40),
            LeaderboardEntry(5, "Sam Loco", 50),
            LeaderboardEntry(6, "${user!!.lastName} ${user!!.firstName} (You)", walletPoints)
        ).sortedByDescending { it.score }

        return entries
    }
}