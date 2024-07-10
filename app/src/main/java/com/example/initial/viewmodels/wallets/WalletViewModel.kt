package com.example.initial.viewmodels.wallets

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import com.example.initial.helpers.sendNotificationToWeb
import com.example.initial.helpers.signalr_notification_label
import com.example.initial.repositories.WalletRepository
import com.example.initial.services.WebNotificationService

class WalletViewModel(private val walletRepository: WalletRepository) : ViewModel() {
    suspend fun getTotalPoints(): Int {
        return walletRepository.getTotalBalance()
    }

    fun getRedeemableAmount(points: Int) : Double {
        return walletRepository.getRedeemablePoints(points)
    }

    suspend fun redeemPoints(context: Context) {
        val data = walletRepository.redeemPoints()
        val pointsToken = "Points|${data.first}"
        val cashToken = "Cash|${data.second}"
        sendNotificationToWeb(context, "${pointsToken}#${cashToken}")
    }
}