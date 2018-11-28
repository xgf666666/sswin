package com.micropole.sxwine.utils.network

import com.blankj.utilcode.util.ActivityUtils.startActivity
import com.darkhorse.httphelper.converter.BaseConvert
import com.darkhorse.preferencesmanager.PreferencesHelper
import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.micropole.sxwine.bean.BaseResponseBean
import com.micropole.sxwine.common.Constants
import com.micropole.sxwine.module.login.LoginActivity
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject

/**
 * Description:
 *
 * @author DarkHorse
 * @date 2018/5/24
 */
class MyConverter : BaseConvert {
    override fun beanToRequest(gson: Gson, adapter: TypeAdapter<out Any>, bean: Any): RequestBody? {
        return null
    }

    override fun responseToBean(gson: Gson, adapter: TypeAdapter<out Any>, responseBody: ResponseBody): Any? {
        val originalBody = responseBody.string()
        val jsonObject = JSONObject(originalBody)
        val state = jsonObject.getString("status")
        if (BaseResponseBean.STATE_FAILED == state) {
            val msg = jsonObject.getString("msg")
            when (jsonObject.getInt("code")) {
                30038 -> {
                    PreferencesHelper.put(Constants.IS_LOGIN,false)
                    startActivity(LoginActivity::class.java)
                }
                30002 -> {
                    PreferencesHelper.put(Constants.IS_LOGIN,false)
                    startActivity(LoginActivity::class.java)
                }
                30048 ->{
                    PreferencesHelper.put(Constants.IS_LOGIN,false)
                    startActivity(LoginActivity::class.java)
                }
            }
            throw MyAPIException(state.toInt(), msg)
        } else if (BaseResponseBean.STATE_SUCCESS == state) {
            return adapter.fromJson(originalBody)
        }
        return adapter.fromJson(originalBody)
    }
}
