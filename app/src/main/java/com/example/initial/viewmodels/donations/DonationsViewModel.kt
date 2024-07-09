package com.example.initial.viewmodels.donations

import androidx.lifecycle.ViewModel
import com.example.initial.persistence.entities.joins.ExchangeableWallet
import com.example.initial.repositories.DonationsRepository
import com.example.initial.viewmodels.helpers.user.sessions.UserSessionViewModel

class DonationsViewModel(
    private val donationsRepository: DonationsRepository,
    private val userSessionViewModel: UserSessionViewModel) : ViewModel() {
    suspend fun listDonations(): List<ExchangeableWallet> {
        return donationsRepository.list(userSessionViewModel.user.value!!.id)
    }
}