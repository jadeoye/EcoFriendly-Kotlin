package com.example.initial.persistence.interfaces

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.initial.persistence.entities.Exchangeable
import com.example.initial.persistence.entities.joins.ExchangeableWallet

@Dao
interface IExchangeable {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun add(exchangeable: Exchangeable)

    @Transaction
    @Query("SELECT * FROM exchangeables WHERE createdBy = :userId ORDER BY createdOn DESC")
    suspend fun list(userId: Int): List<ExchangeableWallet>
}