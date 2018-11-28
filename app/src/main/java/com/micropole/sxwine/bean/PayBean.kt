package com.micropole.sxwine.bean

/**
 * Created by 天使加 on 2018/4/19.
 */

class PayBean {

    lateinit var type: String
    lateinit var data: DataBean

    inner class DataBean {
        var return_code: String? = null
        var return_msg: String? = null
        var appid: String? = null
        var mch_id: String? = null
        var nonce_str: String? = null
        var sign: String? = null
        var result_code: String? = null
        var prepay_id: String? = null
        var trade_type: String? = null
        var time: String? = null
    }

}
