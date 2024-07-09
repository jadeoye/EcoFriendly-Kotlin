package com.example.initial.viewmodels.vouchers

import androidx.lifecycle.ViewModel
import com.example.initial.persistence.entities.Voucher
import com.example.initial.repositories.VoucherRepository
import com.example.initial.viewmodels.helpers.user.sessions.UserSessionViewModel

class VouchersViewModel(
    private val repository: VoucherRepository,
    private val userSessionViewModel: UserSessionViewModel
) : ViewModel() {
    suspend fun list(): List<Voucher> {
        return repository.list(userSessionViewModel.user.value!!.id)
    }
}