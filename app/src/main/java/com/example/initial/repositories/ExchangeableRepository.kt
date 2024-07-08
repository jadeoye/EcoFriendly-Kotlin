package com.example.initial.repositories

import com.example.initial.persistence.entities.Category
import com.example.initial.persistence.entities.Exchangeable
import com.example.initial.persistence.interfaces.IExchangeable
import com.example.initial.persistence.interfaces.IWallet
import com.example.initial.viewmodels.helpers.user.sessions.UserSessionViewModel

class ExchangeableRepository(
    private val exchangeableInterface: IExchangeable,
    private val walletRepository: WalletRepository,
    private val userSessionViewModel: UserSessionViewModel
) {
    suspend fun add(name: String, category: Category, photo: String) {
        val exchangeable = Exchangeable(
            name = name,
            categoryId = category.id,
            photo = photo,
            createdBy = userSessionViewModel.user.value!!.id
        )
        exchangeableInterface.add(exchangeable)
        walletRepository.add(category.points)
    }
}