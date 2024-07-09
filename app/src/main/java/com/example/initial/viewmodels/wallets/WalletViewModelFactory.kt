package com.example.initial.viewmodels.wallets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.initial.repositories.WalletRepository

class WalletViewModelFactory(
    private val repository: WalletRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WalletViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WalletViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}