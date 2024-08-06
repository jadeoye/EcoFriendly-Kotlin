package com.example.initial.viewmodels.vouchers

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.initial.helpers.sendNotificationToWeb
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

    fun delete(voucherId: Int, context: Context) {
        viewModelScope.launch {
            var data = repository.delete(voucherId)
            val pointsToken = "Points|${data.first}"
            val cashToken = "Cash|${data.second}"
            sendNotificationToWeb(context, "${pointsToken}#${cashToken}")
        }
    }
}