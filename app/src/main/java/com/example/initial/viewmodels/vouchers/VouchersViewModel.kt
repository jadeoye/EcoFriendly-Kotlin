package com.example.initial.viewmodels.vouchers

import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.initial.persistence.entities.Voucher
import com.example.initial.repositories.VoucherRepository
import com.example.initial.viewmodels.helpers.user.sessions.UserSessionViewModel
import kotlinx.coroutines.launch

class VouchersViewModel(
    private val repository: VoucherRepository,
    private val userSessionViewModel: UserSessionViewModel
) : ViewModel() {
    suspend fun list(): List<Voucher> {
        return repository.list(userSessionViewModel.user.value!!.id)
    }

    fun delete(voucherId: Int) {
        viewModelScope.launch {
            repository.delete(voucherId)
        }
    }
}