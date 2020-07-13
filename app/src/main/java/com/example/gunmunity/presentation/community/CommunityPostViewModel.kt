package com.example.gunmunity.presentation.community

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.gunmunity.usecase.CommunityPostUsecase
import com.example.gunmunity.util.ConstValue
import com.example.gunmunity.util.SharedPreferenceManager
import com.example.gunmunity.util.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CommunityPostViewModel : ViewModel() {
    private val postUsecase = CommunityPostUsecase()
    private val TAG = "CommunityPostViewModel"
    val postSuccess : SingleLiveEvent<Void> = SingleLiveEvent()

    fun postArticle(category: String, content: String, title: String) {
        val accessToken = SharedPreferenceManager.getStringPref(ConstValue.CONST_ACCESS_TOKEN)
        postUsecase.postArticle(accessToken, category, content, title)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                postSuccess.call()
            }, {
                Log.d(TAG, it.localizedMessage)
            })
    }
}