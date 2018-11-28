package com.micropole.sxwine.base

import cn.jpush.android.api.JPushInterface
import com.blankj.ALog
import com.blankj.utilcode.util.Utils
import com.darkhorse.httphelper.HttpHelper
import com.darkhorse.preferencesmanager.PreferencesHelper
import com.example.baseframe.BaseApplication
import com.example.baseframe.BuildConfig
import com.micropole.sxwine.utils.network.API
import com.micropole.sxwine.utils.network.MyConverter
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import java.util.*


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

//        val locale: Locale
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            locale = resources.configuration.locales.get(0);
//        } else {
//            locale = resources.configuration.locale;
//        }
//        val lang: String
//
//        if (locale.language == "zh") {
//            lang = "zh_cn"
//        } else {
//            lang = "en"
//        }
//
//        PreferencesHelper.put("language", lang)
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
