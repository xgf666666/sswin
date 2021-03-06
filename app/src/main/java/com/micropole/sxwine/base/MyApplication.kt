package com.micropole.sxwine.base

import android.util.Log
import cn.jpush.android.api.JPushInterface
import com.blankj.ALog
import com.blankj.utilcode.util.Utils
import com.darkhorse.httphelper.HttpHelper
import com.darkhorse.preferencesmanager.PreferencesHelper
import com.example.baseframe.BaseApplication
import com.example.baseframe.BuildConfig
import com.micropole.sxwine.utils.network.API
import com.micropole.sxwine.utils.network.MyConverter
import com.umeng.commonsdk.UMConfigure
import com.xx.anypay.WxAppIDProvider
import com.xx.anypay.XxAnyPay
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject


/**
 * Description:
 * Created by DarkHorse on 2018/5/24.
 */
class MyApplication : BaseApplication() {

    override fun initUtils() {
        HttpHelper
                .addBaseUrl(API.BASE_URL)
                .supportDoubleToken(API.TOKEN_SHORT, API.TOKEN_LONG, ::isTokenExpire, ::refreshToken)
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .setConvert(MyConverter())
                .init()
        Utils.init(this)
        ALog.init(this).setLogSwitch(BuildConfig.DEBUG)
        PreferencesHelper.init(this, "config_info")
        PreferencesHelper.init(this, packageName)

        JPushInterface.setDebugMode(true)
        JPushInterface.init(this)
        XxAnyPay.intance.init(this)
        Log.i("XxAnyPay", "wxe13c15b520e07f80")
        XxAnyPay.intance.wxAppIDProvider = object : WxAppIDProvider {
            override val weChatAppID: String
                get() = "wxe13c15b520e07f80"
        }
    }

    private fun isTokenExpire(data: String): Boolean {
        i(data)
        val jsonObject = JSONObject(data)
        val code = jsonObject.getString("code")
        if (code == "30004" || code == "30039") {
            return true
        }
        return false
    }

    private fun refreshToken(): String {
        val response = API.service.refreshToken().execute()
        val bean = response.body()
        return bean!!.data.sign_api
    }

}
