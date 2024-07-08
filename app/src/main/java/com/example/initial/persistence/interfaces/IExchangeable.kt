package com.example.initial.persistence.interfaces

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.initial.persistence.entities.Exchangeable

@Dao
interface IExchangeable {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun add(exchangeable: Exchangeable)

}