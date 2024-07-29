package com.example.initial.repositories

import com.example.initial.persistence.entities.joins.ExchangeableWallet
import com.example.initial.persistence.interfaces.IExchangeable

class DonationsRepository(private val iExchangeable: IExchangeable) {
    suspend fun list(userId: Int): List<ExchangeableWallet> {
        return iExchangeable.list(userId)
    }
}