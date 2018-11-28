package com.micropole.sxwine.module.login

import android.text.TextUtils
import android.webkit.WebSettings
import com.example.mvpframe.BaseMvpActivity
import com.micropole.sxwine.R
import com.micropole.sxwine.base.hideLoadingDialog
import com.micropole.sxwine.base.initToolBar
import com.micropole.sxwine.base.showLoadingDialog
import com.micropole.sxwine.module.login.bean.UserProtocolEntity
import com.micropole.sxwine.module.login.mvp.contract.UserProtocolContract
import com.micropole.sxwine.module.login.mvp.presenter.UserProtocolPresenter
import kotlinx.android.synthetic.main.activity_user_protocol.*
import kotlinx.android.synthetic.main.item_toolbar.*
import org.jsoup.Jsoup

/**
 * Created by JohnnyH on 2018/9/17.
 * 用户协议
 */
class UserProtocolActivity : BaseMvpActivity<UserProtocolContract.Model,UserProtocolContract.View,UserProtocolPresenter>(),UserProtocolContract.View {
    override fun onGetProtocolFailure() {
        hideLoadingDialog()
    }

    override fun onGetProtocolSuccess(userProtocolEntity: UserProtocolEntity?) {
        hideLoadingDialog()
        initWebView(userProtocolEntity!!.protocol_value)
    }

    override fun createPresenter(): UserProtocolPresenter = UserProtocolPresenter()

    override fun getLayoutId(): Int = R.layout.activity_user_protocol

    override fun initView() {
        initToolBar(getString(R.string.user_protocol_title))
        toolbar.setNavigationOnClickListener { finish() }
    }

    override fun initListener() {

    }

    override fun initData() {
        showLoadingDialog()
        mPresenter.getProtocol()

    }

    private fun initWebView( content: String){
        if (!TextUtils.isEmpty(content)){
            //htmlWebPic(pro_text);

            webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//没网，则从本地获取，即离线加载

            webView.getSettings().setLoadWithOverviewMode(true);// 缩放至屏幕的大小
            webView.loadDataWithBaseURL(null,getNewContent(content), "text/html", "utf-8", null);
        }
    }

    private fun getNewContent(htmlText: String): String {
        val doc = Jsoup.parse(htmlText)
        val elements = doc.getElementsByTag("img")
        for (element in elements) {
            element.attr("width", "100%").attr("height", "auto")
        }
        return doc.toString()
    }
}