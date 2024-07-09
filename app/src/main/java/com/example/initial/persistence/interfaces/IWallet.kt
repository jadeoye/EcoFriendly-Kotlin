package com.example.initial.persistence.interfaces

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.initial.persistence.entities.Wallet

@Dao
interface IWallet {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun add(wallet: Wallet): Long

    @Query("SELECT * FROM wallets WHERE createdBy = :userId AND isDeleted = 0 ORDER BY createdOn DESC LIMIT 1")
    suspend fun lastWallet(userId: Int): Wallet?
}