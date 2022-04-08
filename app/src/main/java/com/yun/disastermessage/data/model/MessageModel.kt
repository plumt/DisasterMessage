package com.yun.disastermessage.data.model

import com.yun.disastermessage.base.Item

class MessageModel {
    data class RS(
        val DisasterMsg2: List<DisasterMsg>?
    ) {
        data class DisasterMsg(
            val head: List<Head>? = null,
            val row: List<Row>? = null
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
            val create_date: String?,
            val location_id: String?,
            val location_name: String?,
            val md101_sn: String?,
            val msg: String?,
            val send_platform: String?
        ) : Item()
    }
}