package com.micropole.sxwine.module.personal

import android.annotation.SuppressLint
import android.os.Build
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.baseframe.BaseActivity
import com.micropole.sxwine.R
import com.micropole.sxwine.base.initToolBar
import kotlinx.android.synthetic.main.activity_answer.*
import kotlinx.android.synthetic.main.item_toolbar.*


/**
 * Created by JohnnyH on 2018/6/15.
 * 网络客服
 */
class ServiceOnlineActivity : BaseActivity(){

    private lateinit var mUrl : String
    private lateinit var mTitle : String

    override fun getLayoutId(): Int = R.layout.activity_service_online

    @SuppressLint("SetJavaScriptEnabled")
    override fun initView() {
        mUrl=intent.getStringExtra("Url")
        mTitle=intent.getStringExtra("title")
        initToolBar(mTitle)
        toolbar.setNavigationOnClickListener { finish() }

        webView.requestFocus()
        webView.isHorizontalScrollBarEnabled = false
        webView.isVerticalScrollBarEnabled = false
        webView.isScrollContainer = false
        val settings = webView.settings
        settings.javaScriptEnabled = true
        settings.blockNetworkImage = false
        settings.useWideViewPort = true
        settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.NARROW_COLUMNS
        settings.builtInZoomControls = true // 设置显示缩放按钮
        settings.domStorageEnabled = true
        settings.setSupportZoom(true) // 支持缩放
        if (Build.VERSION.SDK_INT >= 21)
        //Build.VERSION_CODES.LOLLIPOP

            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW)

        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return true
            }
        }
        webView.loadUrl(mUrl)
    }

    override fun initData() {

    }

    override fun initListener() {

    }
}