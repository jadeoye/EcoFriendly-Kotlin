package com.example.initial.viewmodels.wallets

import androidx.lifecycle.ViewModel
import com.example.initial.repositories.WalletRepository

class WalletViewModel(private val walletRepository: WalletRepository) : ViewModel() {
    suspend fun getTotalPoints(): Int {
        return walletRepository.getTotalBalance()
    }

    fun getRedeemableAmount(points: Int) : Double {
        return walletRepository.getRedeemablePoints(points)
    }

    suspend fun redeemPoints() {
        walletRepository.redeemPoints()
    }
}