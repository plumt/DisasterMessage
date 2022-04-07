package com.yun.disastermessage.ui.home

import android.app.Application
import android.util.Log
import com.yun.disastermessage.R
import com.yun.disastermessage.base.BaseViewModel
import com.yun.disastermessage.base.ListLiveData
import com.yun.disastermessage.data.Constant
import com.yun.disastermessage.data.Constant.TAG
import com.yun.disastermessage.data.model.MessageModel
import com.yun.disastermessage.data.repository.OpenApi
import com.yun.disastermessage.util.PreferenceManager
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.http.Query
import java.net.URLDecoder
import java.net.URLEncoder

class HomeViewModel(
    application: Application,
    private val api: OpenApi,
    private val sharedPreferences: PreferenceManager
) : BaseViewModel(application) {

    var pageNo = 1

    val messageItems = ListLiveData<MessageModel.RS.Row>()

    init {
        callMessageApi("성남")
    }

    fun callMessageApi(location: String, clear: Boolean = false) {
        api.message(
            ServiceKey = URLDecoder.decode(mContext.getString(R.string.ServiceKey), "UTF-8"),
            pageNo = pageNo.toString(),
            location_name = location
        )
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .flatMap { Observable.just(it) }
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                messageItems.clear(clear)
                it?.DisasterMsg2?.get(1)?.row?.forEachIndexed { index, row ->
                    messageItems.value!!.add(
                        addItem(row, index)
                    )
                }
            }.subscribe({
                Log.d(TAG, "sucess : ${messageItems.value}")
            }, {
                Log.e(TAG, "error : ${it.message}")
            })
    }

    private fun addItem(item: MessageModel.RS.Row, index: Int): MessageModel.RS.Row =
        item.apply { id = index }

}