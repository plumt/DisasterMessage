package com.yun.disastermessage.ui.home.viewpager.list

import android.app.Application
import com.yun.disastermessage.base.BaseViewModel
import com.yun.disastermessage.base.ListLiveData
import com.yun.disastermessage.data.model.MessageModel
import com.yun.disastermessage.data.repository.OpenApi

class ListViewModel(
    application: Application
) : BaseViewModel(application){

    val messageItems = ListLiveData<MessageModel.RS.Row>()

}