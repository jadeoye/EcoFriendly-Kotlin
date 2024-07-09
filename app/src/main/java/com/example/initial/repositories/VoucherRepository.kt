package com.example.initial.repositories

import com.example.initial.persistence.entities.Voucher
import com.example.initial.persistence.interfaces.IVoucher

class VoucherRepository(private val iVoucher: IVoucher) {
    suspend fun list(userId: Int) : List<Voucher> {
        return iVoucher.list(userId)
    }
}