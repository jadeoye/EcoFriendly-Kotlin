package com.example.initial.persistence.interfaces

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.initial.persistence.entities.Wallet

@Dao
interface IWallet {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun add(wallet: Wallet)
}