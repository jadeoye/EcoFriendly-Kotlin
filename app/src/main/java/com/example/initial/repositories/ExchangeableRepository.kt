package com.example.initial.repositories

import com.example.initial.persistence.entities.Category
import com.example.initial.persistence.entities.Exchangeable
import com.example.initial.persistence.interfaces.IExchangeable
import com.example.initial.viewmodels.helpers.user.sessions.UserSessionViewModel

class ExchangeableRepository(
    private val exchangeableInterface: IExchangeable,
    private val walletRepository: WalletRepository,
    private val userSessionViewModel: UserSessionViewModel
) {
    suspend fun add(name: String, category: Category, photo: String) {
        val walletId = walletRepository.add(category.points)
        val exchangeable = Exchangeable(
            name = name,
            categoryId = category.id,
            photo = photo,
            walletId = walletId,
            createdBy = userSessionViewModel.user.value!!.id,
            points = category.points // Ensure points are being set here
        )
        exchangeableInterface.add(exchangeable)
    }
}
