package com.yun.disastermessage.data

import java.net.URLDecoder
import java.net.URLEncoder

object Constant {
    val TAG = "lys"
    val numOfRows = "20"
    val type = "json"
    val createDate = URLDecoder.decode("2000%2F01%2F10 00:00:00","UTF-8")

    val LIST_SCREEN = 0
    val SELECT_SCREEN = 1

    val ALL_LOCATION = "*00000000"
    val SELECT_LOCATION = "*000000"

    val SHARED_LOCATION_KEY = "location"

    val REMOTE_KEY_APP_VERSION = "app_version"
    val TYPE_ADS = 4

}