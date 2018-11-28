package com.micropole.sxwine.bean

/**
 * Description:
 * Created by DarkHorse on 2018/5/24.
 */
class BaseResponseBean<T>(var status: String, var code: String, var msg: String, var data: T){
    companion object {

        val STATE_FAILED : String = "0" //失败

        val STATE_SUCCESS: String = "1" //成功
    }
}

