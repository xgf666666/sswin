package com.example.httphelper.interceptor

import android.app.Dialog
import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import com.darkhorse.preferencesmanager.PreferencesHelper
import com.example.baseframe.utils.AppManager
import com.example.baseframe.utils.toast
import com.example.httphelper.R
import okhttp3.Interceptor
import okhttp3.Response
import java.util.*

/**
 * Description:
 * Created by DarkHorse on 2018/6/7.
 */
class NetWorkCheckInterceptor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response? {
        if (!isNetworkAvailable(AppManager.currentActivity())) {
            chain.call().cancel()
            val baseActivity = AppManager.currentActivity()
            baseActivity.mLoadingDialog?.dismiss()
            baseActivity.mHandler.post({
                toast(AppManager.currentActivity().getString(R.string.hint_network_error))
            })
        }


        val request = chain.request()
        val builder = request.newBuilder()
        var response: Response;
        val locale: Locale
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = AppManager.currentActivity().resources.configuration.locales.get(0);
        } else {
            locale = AppManager.currentActivity().resources.configuration.locale;
        }
        val lang: String

        if (locale.language == "zh") {
            lang = "zh_cn"
        } else {
            lang = "en"
        }
        builder.addHeader("language", lang)
        response = chain.proceed(builder.build())

        return response
    }

    /**
     * 判断网络是否可用
     */
    private fun isNetworkAvailable(context: Context): Boolean {
        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info = manager.activeNetworkInfo
        if (info != null) {
            return info.isAvailable
        }
        return false
    }

    /**
     * 判断wifi网络是否可用
     */
    private fun isWifiAvailable(context: Context): Boolean {
        val manager: ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = manager.activeNetworkInfo
        if (networkInfo.type == ConnectivityManager.TYPE_WIFI) {
            return networkInfo.isAvailable
        }
        return false
    }

    /**
     * 判断mobile网络是否可用
     */
    private fun isMobileAvailable(context: Context): Boolean {
        val manager: ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = manager.activeNetworkInfo
        if (networkInfo.type == ConnectivityManager.TYPE_MOBILE) {
            return networkInfo.isAvailable
        }
        return false
    }
}
