package com.haja.haja.View.ui.NotificationsHistory

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.haja.haja.Service.model.NotificationsResponse
import com.haja.haja.Service.repository.AppRepository
import com.haja.haja.Utils.SharedPreferenceUtil
import com.haja.haja.Utils.SingleLiveEvent2
import com.haja.haja.Utils.TOKEN
import com.haja.haja.Utils.USERID

class NotificationsListViewModel(application: Application) : AndroidViewModel(application) {

    private val token = SharedPreferenceUtil(getApplication()).getString(TOKEN, "")
    private val repository = AppRepository(token.toString())

    fun getNotifications(): SingleLiveEvent2<NotificationsResponse> {
        val userId = SharedPreferenceUtil(getApplication()).getString(USERID, "0")?.toInt()
        return repository.getNotifications(userId!!)
    }
}
