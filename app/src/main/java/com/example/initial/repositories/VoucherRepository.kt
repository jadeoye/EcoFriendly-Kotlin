package com.example.initial.repositories

import com.example.initial.persistence.entities.Voucher
import com.example.initial.persistence.entities.Wallet
import com.example.initial.persistence.interfaces.IVoucher
import com.example.initial.persistence.interfaces.IWallet
import com.example.initial.viewmodels.helpers.user.sessions.UserSessionViewModel

class VoucherRepository(private val voucherInterface: IVoucher,
                        private  val walletInterface: IWallet,
                        private val userSessionViewModel: UserSessionViewModel
    ) {
    suspend fun list(userId: Int) : List<Voucher> {
        return voucherInterface.list(userId)
    }

    suspend fun delete(voucherId: Int) {
        val userId = userSessionViewModel.user.value!!.id
        val currentWallet = walletInterface.currentWallet(userId)
        val voucher = voucherInterface.get(voucherId)

       if(voucher == null) {

       }
        else{
           val wallet = Wallet(
               previousWalletId = currentWallet?.id,
               amount = voucher.points,
               createdBy = userId
           )

           walletInterface.add(wallet)
           return voucherInterface.delete(voucherId)
       }
    }
}