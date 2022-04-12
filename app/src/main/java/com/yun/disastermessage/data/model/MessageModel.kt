package com.yun.disastermessage.data.model

import com.yun.disastermessage.base.Item

class MessageModel {
    data class RS(
        val DisasterMsg2: List<DisasterMsg>?
    ) {
        data class DisasterMsg(
            val head: List<Head>? = null,
            val row: ArrayList<Row>? = null
        )

        data class Head(
            val totalCount: Long? = null,
            val numOfRows: String? = null,
            val pageNo: String? = null,
            val type: String? = null,
            val RESULT: RESULTS? = null
        )

        data class RESULTS(
            val resultCode: String?,
            val resultMsg: String?
        )

        data class Row(
            override var id: Int = 0,
            override var viewType: Int = 0,
            val create_date: String? = null,
            val location_id: String? = null,
            val location_name: String? = null,
            val md101_sn: String? = null,
            val msg: String? = null,
            val send_platform: String? = null
        ) : Item(){
            fun mod(): Boolean = id % 2 == 0
        }
    }
}