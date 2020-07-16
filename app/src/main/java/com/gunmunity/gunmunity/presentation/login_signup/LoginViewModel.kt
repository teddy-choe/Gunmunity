package com.gunmunity.gunmunity.presentation.login_signup

import android.util.Log
import androidx.lifecycle.ViewModel
import com.gunmunity.gunmunity.usecase.LoginUsecase
import com.gunmunity.gunmunity.util.ConstValue
import com.gunmunity.gunmunity.util.SharedPreferenceManager
import com.gunmunity.gunmunity.util.SingleLiveEvent
import com.gunmunity.gunmunity.util.sha256
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class LoginViewModel : ViewModel() {
    private val loginUsecase: LoginUsecase = LoginUsecase()
    val loginSuccess : SingleLiveEvent<Void> = SingleLiveEvent()

    fun doLogin(email : String, password : String) {
        val hashedPassword = password.sha256()
        loginUsecase.doLogin(email, hashedPassword)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                SharedPreferenceManager.setPref(ConstValue.CONST_ACCESS_TOKEN, it.accessToken.token)
                SharedPreferenceManager.setPref(ConstValue.CONST_USER_ID, it.userInfo.id)
                loginSuccess.call()
            }, {
                Log.d("Login", it.localizedMessage)
            })
    }
}