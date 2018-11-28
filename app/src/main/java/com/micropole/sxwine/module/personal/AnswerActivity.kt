package com.micropole.sxwine.module.personal

import android.text.TextUtils
import android.webkit.WebSettings
import com.example.baseframe.BaseActivity
import com.micropole.sxwine.R
import com.micropole.sxwine.base.initToolBar
import kotlinx.android.synthetic.main.activity_answer.*
import kotlinx.android.synthetic.main.item_toolbar.*
import org.jsoup.Jsoup

/**
 * Created by JohnnyH on 2018/6/12.
 */
class AnswerActivity : BaseActivity() {

    private lateinit var mAnswer : String

    override fun getLayoutId(): Int = R.layout.activity_answer

    override fun initView() {
        initToolBar(getString(R.string.fqa_answer))
        toolbar.setNavigationOnClickListener { finish() }
        mAnswer=intent.getStringExtra("answer")
        if (!TextUtils.isEmpty(mAnswer)){
            //htmlWebPic(pro_text);
            webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
            webView.getSettings().setLoadWithOverviewMode(true);// 缩放至屏幕的大小
            webView.loadDataWithBaseURL(null,getNewContent(mAnswer), "text/html", "utf-8", null);
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

    override fun initListener() {

    }

    override fun initData() {

    }
}