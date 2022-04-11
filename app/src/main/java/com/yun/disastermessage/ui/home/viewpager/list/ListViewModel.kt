package com.yun.disastermessage.ui.home.viewpager.list

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.yun.disastermessage.base.BaseViewModel
import com.yun.disastermessage.base.ListLiveData
import com.yun.disastermessage.data.Constant
import com.yun.disastermessage.data.model.MessageModel
import com.yun.disastermessage.util.PreferenceManager

class ListViewModel(
    application: Application
) : BaseViewModel(application){

    val messageItems = ListLiveData<MessageModel.RS.Row>()

    val screen = MutableLiveData<Int>(Constant.SELECT_SCREEN)

}