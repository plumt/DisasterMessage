package com.yun.disastermessage.ui.home

import android.app.Application
import com.yun.disastermessage.base.BaseViewModel
import com.yun.disastermessage.data.repository.OpenApi
import com.yun.disastermessage.util.PreferenceManager

class HomeViewModel(
    application: Application,
//    private val api: OpenApi,
    private val sharedPreferences: PreferenceManager
) : BaseViewModel(application){

}