package com.yun.disastermessage.ui.home.viewpager.select

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.yun.disastermessage.base.BaseViewModel
import com.yun.disastermessage.base.ListLiveData
import com.yun.disastermessage.data.model.AddressModel

class SelectViewModel(
    application: Application
) : BaseViewModel(application){

    val loading = MutableLiveData<Boolean>(false)
    val locationList = ListLiveData<AddressModel.Items>()

    val locationNm = MutableLiveData("")


}