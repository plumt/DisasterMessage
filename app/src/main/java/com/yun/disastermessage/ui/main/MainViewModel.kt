package com.yun.disastermessage.ui.main

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.yun.disastermessage.base.BaseViewModel
import com.yun.disastermessage.util.PreferenceManager

class MainViewModel(
    application: Application,
    private val sharedPreferences: PreferenceManager
) : BaseViewModel(application) {

    val isLoading = MutableLiveData<Boolean>(false)

}