package com.example.initial.persistence.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "exchangeables",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("createdBy"),
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Category::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("categoryId"),
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Wallet::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("walletId"),
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value=["categoryId", "walletId"])]
)
data class Exchangeable(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val categoryId: Int,
    val photo: String,
    val walletId: Int,
    val createdBy: Int,
    val createdOn: Long = System.currentTimeMillis(),
    val points: Int // Ensure this field is included
)