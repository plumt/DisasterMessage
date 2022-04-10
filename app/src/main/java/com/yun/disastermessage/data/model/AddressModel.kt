package com.yun.disastermessage.data.model

import com.yun.disastermessage.base.Item

class AddressModel {

    data class RS(
        val regcodes: ArrayList<Items>?
    )

    data class Items(
        override var id: Int = 0,
        override var viewType: Int = 0,
        val code: String,
        var name: String
    ) : Item()
}