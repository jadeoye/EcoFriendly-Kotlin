package com.example.initial.persistence.interfaces

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.initial.persistence.entities.Leaderboard

@Dao
interface ILeaderboard {
    @Query("SELECT * FROM leaderboard WHERE createdOn > :period ORDER BY points DESC LIMIT 10")
    suspend fun listTopDonors(period: Long): List<Leaderboard>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(leaderboard: Leaderboard)
}
