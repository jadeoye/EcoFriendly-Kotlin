package com.example.initial.repositories

import com.example.initial.persistence.entities.Voucher
import com.example.initial.persistence.entities.Wallet
import com.example.initial.persistence.interfaces.IVoucher
import com.example.initial.persistence.interfaces.IWallet
import com.example.initial.viewmodels.helpers.user.sessions.UserSessionViewModel

class WalletRepository(
    private val walletInterface: IWallet,
    private val voucherInterface: IVoucher,
    private val userSessionViewModel: UserSessionViewModel
) {
    suspend fun add(categoryPoints: Int): Int {
        val userId = userSessionViewModel.user.value!!.id
        val lastWallet = walletInterface.currentWallet(userId)
        val wallet = Wallet(
            previousWalletId = lastWallet?.id,
            amount = (lastWallet?.amount ?: 0) + categoryPoints,
            createdBy = userId
        )
        val walletId = walletInterface.add(wallet)
        return walletId.toInt()
    }

    suspend fun getTotalBalance(): Int {
        val lastWallet = walletInterface.currentWallet(userSessionViewModel.user.value!!.id)
        return lastWallet?.amount ?: 0
    }

    fun getRedeemablePoints(points: Int): Double {
        return points * 0.02
    }

    suspend fun redeemPoints() : Pair<Double, Double> {
        val userId = userSessionViewModel.user.value!!.id
        val currentWallet = walletInterface.currentWallet(userSessionViewModel.user.value!!.id)
        val lastWalletAmount = currentWallet?.amount ?: 0
        val unusedVoucher = voucherInterface.getUnusedVoucher(userId)
        val cashRedeemed = getRedeemablePoints(lastWalletAmount)

        if (unusedVoucher != null) {
            unusedVoucher.amount += (currentWallet?.amount ?: 0) + cashRedeemed
            unusedVoucher.createdOn =
                System.currentTimeMillis() // ideally we should use a new property 'modifiedOn'
            voucherInterface.update(unusedVoucher)
        } else {
            val voucher =
                Voucher(code = generateRandomString(), points = lastWalletAmount, amount = cashRedeemed, ownerId = userId)
            voucherInterface.add(voucher)
        }

        val wallet = Wallet(
            previousWalletId = currentWallet?.id,
            amount = 0,
            createdBy = userId
        )
        walletInterface.add(wallet)

        return Pair(lastWalletAmount.toDouble(), cashRedeemed)
    }

    private fun generateRandomString(length: Int = 6): String {
        val chars = ('A'..'Z') + ('0'..'9')
        return (1..length)
            .map { chars.random() }
            .joinToString("")
    }
}