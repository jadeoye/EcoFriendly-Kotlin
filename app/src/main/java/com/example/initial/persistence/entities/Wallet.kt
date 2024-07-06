package com.example.initial.persistence.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.math.BigDecimal

@Entity(
    tableName = "wallets",
    foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("createdBy"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class Wallet(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val amount: BigDecimal,
    val createdBy: Int,
    val isDeleted: Boolean = false
)
