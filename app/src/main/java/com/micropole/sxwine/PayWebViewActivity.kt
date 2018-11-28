package com.micropole.tanglong

import android.app.Activity
import android.graphics.Bitmap
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import com.example.baseframe.BaseActivity
import com.micropole.sxwine.R
import com.micropole.sxwine.base.i
import com.micropole.sxwine.utils.network.API
import kotlinx.android.synthetic.main.activity_webview.*
import java.util.*
import kotlin.concurrent.schedule

/**
 * Created by DarkHorse on 2018/5/2.
 */
class PayWebViewActivity : BaseActivity() {

    companion object {
        /**
         * 加载网址
         */
        val TYPE_URL = 1
        /**
         * 加载富文本
         */
        val TYPE_CONTENT = 2

        /**
         * 网页标题
         */
        val EXTRA_WEB_TITLE = "title"
        /**
         * 加载方式
         * @see PayWebViewActivity.TYPE_URL
         * @see PayWebViewActivity.TYPE_CONTENT
         *
         */
        val EXTRA_WEB_TYPE = "type"
        /**
         * 网页地址
         */
        val EXTRA_WEB_URL = "url"

        /**
         * 网页内容
         */
        val EXTRA_WEB_CONTENT = "content"

    }

    private lateinit var mWebSettings: WebSettings

    override fun getLayoutId(): Int = R.layout.activity_webview

    override fun initView() {
    }

    override fun initListener() {
    }

    override fun initData() {
        webView.setWebViewClient(mWebViewClient)
        webView.setWebChromeClient(mWebChromeClient)
        mWebSettings = webView.getSettings()
        mWebSettings.javaScriptEnabled = true
        mWebSettings.cacheMode = WebSettings.LOAD_NO_CACHE
        var type = intent.getIntExtra(WebViewActivity.EXTRA_WEB_TYPE, 0)
        when (type) {
            TYPE_URL -> {
                var url = intent.getStringExtra(WebViewActivity.EXTRA_WEB_URL)
                webView.loadUrl(url)
                Log.e("WebViewActivity", url)
            }
            TYPE_CONTENT -> {
                var content = intent.getStringExtra(WebViewActivity.EXTRA_WEB_CONTENT)
//                webView.loadData(content, "text/html", "UTF-8")
                webView.loadDataWithBaseURL(null, content, "text/html", "UTF-8", null);
                Log.e("WebViewActivity", content)
            }
        }
    }


    private val mWebViewClient = object : WebViewClient() {

        override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            progressBar.setVisibility(View.VISIBLE)
        }

        override fun onPageFinished(view: WebView, url: String) {
            super.onPageFinished(view, url)
            progressBar.setVisibility(View.INVISIBLE)
            if (url == "http://api.180changes.com/success") {
                setResult(Activity.RESULT_OK, intent)
                finish()
            } else if (url == "http://api.180changes.com/fail") {
                setResult(Activity.CONTEXT_RESTRICTED, intent)
                finish()
            }
        }

        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            i(url!!)
            return super.shouldOverrideUrlLoading(view, url)
        }

        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                i(request?.url.toString())
            }
            return super.shouldOverrideUrlLoading(view, request)
        }
    }

    private val mWebChromeClient = object : WebChromeClient() {
        override fun onProgressChanged(view: WebView, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
            progressBar.setProgress(newProgress)
        }
    }

    override fun onStart() {
        super.onStart()
        mWebSettings.javaScriptEnabled = true
        webView.resumeTimers()
    }

    override fun onResume() {
        super.onResume()
        webView.onResume()
    }

    override fun onPause() {
        super.onPause()
        webView.onPause()
    }

    override fun onStop() {
        super.onStop()
        mWebSettings.javaScriptEnabled = false
        webView.pauseTimers()
    }

    override fun onDestroy() {
        webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null)
        webView.clearHistory()
        (webView.getParent() as ViewGroup).removeView(webView)
        webView.destroy()
        super.onDestroy()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack()
            return true;
        }
        return super.onKeyDown(keyCode, event)
    }

}
