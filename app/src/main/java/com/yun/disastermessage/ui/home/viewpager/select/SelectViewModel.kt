package com.yun.disastermessage.ui.home.viewpager.select

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.yun.disastermessage.base.BaseViewModel
import com.yun.disastermessage.base.ListLiveData
import com.yun.disastermessage.data.Constant
import com.yun.disastermessage.data.Constant.TAG
import com.yun.disastermessage.data.model.AddressModel
import com.yun.disastermessage.util.PreferenceManager

class SelectViewModel(
    application: Application,
    private val sharedPreferences: PreferenceManager
) : BaseViewModel(application){

    val loading = MutableLiveData(false)
    val locationList = ListLiveData<AddressModel.Items>()

    val locationNm = MutableLiveData("")

    val screen = MutableLiveData<Int>(Constant.LIST_SCREEN)

    init {
        Log.d(TAG,"shared : ${sharedPreferences.getString(mContext,Constant.SHARED_LOCATION_KEY)}")
        if(sharedPreferences.getString(mContext,Constant.SHARED_LOCATION_KEY) != ""){
            locationNm.value = sharedPreferences.getString(mContext,Constant.SHARED_LOCATION_KEY)
        }
    }
}