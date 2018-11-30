package com.micropole.sxwine.utils.network

import android.os.Build.HOST
import com.darkhorse.httphelper.HttpHelper

/**
 * Description:
 *
 * @author DarkHorse
 * @date 2018/5/24
 */
object API {
    //    const val HOST = "http://test.catrongzi.com"
//    const val HOST = "http://meibai.ljzchris.cn"
    const val HOST = "http://sxj.goodbooy.cn"
    const val BASE_URL = "$HOST/api/"
    const val SUCCESS_STATUS = "1"
    const val TOKEN_SHORT = "apisign"
    const val TOKEN_LONG = "loginsign"

    val service = HttpHelper.getService(BaseService::class.java)
}
