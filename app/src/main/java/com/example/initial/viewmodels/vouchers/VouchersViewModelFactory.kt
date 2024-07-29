package com.example.initial.viewmodels.vouchers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.initial.repositories.VoucherRepository
import com.example.initial.viewmodels.helpers.user.sessions.UserSessionViewModel

class VouchersViewModelFactory(private val repository: VoucherRepository, private val userSessionViewModel: UserSessionViewModel) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VouchersViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return VouchersViewModel(repository, userSessionViewModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}