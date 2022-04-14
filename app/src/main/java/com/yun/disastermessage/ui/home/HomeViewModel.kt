package com.yun.disastermessage.ui.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yun.disastermessage.R
import com.yun.disastermessage.base.BaseViewModel
import com.yun.disastermessage.base.ListLiveData
import com.yun.disastermessage.data.Constant
import com.yun.disastermessage.data.Constant.ADS_SHOW_CNT
import com.yun.disastermessage.data.Constant.ALL_LOCATION
import com.yun.disastermessage.data.Constant.INTERNET_ERROR
import com.yun.disastermessage.data.Constant.LIST_SCREEN
import com.yun.disastermessage.data.Constant.SELECT_SCREEN
import com.yun.disastermessage.data.Constant.SHAREDPREFERENCES_CNT_KEY
import com.yun.disastermessage.data.Constant.SHARED_LOCATION_KEY
import com.yun.disastermessage.data.Constant.TAG
import com.yun.disastermessage.data.Constant.TYPE_ADS
import com.yun.disastermessage.data.model.AddressModel
import com.yun.disastermessage.data.model.MessageModel
import com.yun.disastermessage.data.repository.api.Api
import com.yun.disastermessage.ui.popup.OneButtonPopup
import com.yun.disastermessage.ui.popup.TwoButtonPopup
import com.yun.disastermessage.util.PreferenceManager
import kotlinx.coroutines.launch
import java.net.URLDecoder

class HomeViewModel(
    application: Application,
    private val api: Api,
    private val api_address: Api,
    private val sharedPreferences: PreferenceManager
) : BaseViewModel(application) {

    var pageNo = 0

    val messageItems = ListLiveData<MessageModel.RS.Row>()

    val screen = MutableLiveData<Int>()

    val loading = MutableLiveData(false)

    val location = MutableLiveData("")

    val locationList = ListLiveData<AddressModel.Items>()

    val openAdmob = MutableLiveData(false)

    val errorType = MutableLiveData("")

//    https://grpc-proxy-server-mkvo6j4wsq-du.a.run.app/v1/regcodes?regcode_pattern=*00000000
//    https://grpc-proxy-server-mkvo6j4wsq-du.a.run.app/v1/regcodes?regcode_pattern=26*000000
//    https://grpc-proxy-server-mkvo6j4wsq-du.a.run.app/v1/regcodes?regcode_pattern=11*000000

    init {
        if (sharedPreferences.getString(mContext, SHARED_LOCATION_KEY) != "") {
            screen.value = LIST_SCREEN
            location.value = sharedPreferences.getString(mContext, SHARED_LOCATION_KEY)
            callMessageApi()
        } else {
            screen.value = SELECT_SCREEN
        }
    }

    fun callAddressApi(pattern: String = ALL_LOCATION) {
        loading.value = true
        viewModelScope.launch {
            try {
                (callApi(
                    api_address.allAddress(pattern)
                ) as AddressModel.RS).run {
                    Log.d(TAG, "result : ${this.regcodes}")
                    this.regcodes?.run {
                        if (pattern != ALL_LOCATION) {
                            this[0].name = this[0].name + " 전체"
                        }
                        locationList.value = setId(this, 0)
                    }
                }
            } catch (e: Throwable) {
                Log.e(TAG, "error : ${e.message}")
                errorType.value = INTERNET_ERROR
                loading.value = false
            }
        }
    }


    fun callMessageApi(clear: Boolean = false) {
        loading.value = true
        pageNo++
        viewModelScope.launch {
            try {
                (callApi(
                    api.message(
                        ServiceKey = URLDecoder.decode(
                            mContext.getString(R.string.ServiceKey),
                            "UTF-8"
                        ),
                        pageNo = pageNo.toString(),
                        location_name = location.value!!
                    )
                ) as MessageModel.RS).run {
                    if (clear) messageItems.value!!.clear()
                    screen.value = LIST_SCREEN
                    DisasterMsg2?.get(1)?.row?.run {
                        this.add(
                            if (this.size > 2) 2 else this.size,
                            MessageModel.RS.Row(id = 0, viewType = TYPE_ADS)
                        )
                        messageItems.addAll(setId(this, messageItems.value!!.size))
                        cntCheck()
                    }
                    sharedPreferences.setString(mContext, SHARED_LOCATION_KEY, location.value)
                    loading.value = false
                }
            } catch (e: Throwable) {
                Log.e(TAG, "HomeViewModel error : ${e.message}")
                errorType.value = INTERNET_ERROR
                loading.value = false
            }
        }
    }
    private fun cntCheck(){
        sharedPreferences.getInt(mContext, SHAREDPREFERENCES_CNT_KEY)!!.let {
            if(it >= ADS_SHOW_CNT) {
                openAdmob.value = true
                Log.d(TAG,"222222")
            }
            else sharedPreferences.setInt(mContext,SHAREDPREFERENCES_CNT_KEY,it + 1)
        }
        Log.d(TAG, "cnt : ${sharedPreferences.getInt(mContext, SHAREDPREFERENCES_CNT_KEY)}")

    }


}