package com.example.initial.repositories

import com.example.initial.persistence.entities.Wallet
import com.example.initial.persistence.interfaces.IWallet
import com.example.initial.viewmodels.helpers.user.sessions.UserSessionViewModel

class WalletRepository(private val walletInterface: IWallet, private val userSessionViewModel: UserSessionViewModel) {
    suspend fun add(categoryPoints: Int) {
        val wallet = Wallet(amount = categoryPoints, createdBy = userSessionViewModel.user.value!!.id)
        walletInterface.add(wallet)
    }
}