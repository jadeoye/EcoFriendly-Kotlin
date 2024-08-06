package com.example.initial.persistence.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.math.BigDecimal

@Entity(
    tableName = "vouchers",
    foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("ownerId"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class Voucher(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val code: String,
    var amount: Double,
    val points: Int,
    val isConsumed: Boolean = false,
    var createdOn: Long = System.currentTimeMillis(),
    val ownerId: Int,
    val isDeleted: Boolean = false
)
