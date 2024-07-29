package com.example.initial.persistence.entities.joins

import androidx.room.Embedded
import androidx.room.Relation
import com.example.initial.persistence.entities.Category
import com.example.initial.persistence.entities.Exchangeable
import com.example.initial.persistence.entities.Wallet

data class ExchangeableWallet(
    @Embedded val exchangeable: Exchangeable,
    @Relation(
        parentColumn = "walletId",
        entityColumn = "id"
    )
    val wallet: Wallet,
    @Relation(
        parentColumn = "categoryId",
        entityColumn = "id"
    )
    val category: Category
)