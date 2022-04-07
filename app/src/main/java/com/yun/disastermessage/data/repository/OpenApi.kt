package com.yun.disastermessage.data.repository

import com.yun.disastermessage.data.Constant
import com.yun.disastermessage.data.model.MessageModel
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenApi {
    @GET("/1741000/DisasterMsg4/getDisasterMsg2List")
    fun message(
        @Query("serviceKey") ServiceKey: String,
        @Query("pageNo") pageNo: String,
        @Query("numOfRows") numOfRows: String = Constant.numOfRows,
        @Query("type") type: String = Constant.type,
        @Query("create_date") create_date: String = Constant.createDate,
        @Query("location_name") location_name: String
    ): Observable<MessageModel.RS>
}