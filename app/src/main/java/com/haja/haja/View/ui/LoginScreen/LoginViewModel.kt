package com.haja.haja.View.ui.LoginScreen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.haja.haja.Service.model.UserModel
import com.haja.haja.Service.repository.AuthRepository

class LoginViewModel (application: Application) : AndroidViewModel(application){

    private val authRepository = AuthRepository.getInstance

    fun login(map: HashMap<String,String>): MutableLiveData<UserModel> {
        return authRepository.login(map)
    }

}
