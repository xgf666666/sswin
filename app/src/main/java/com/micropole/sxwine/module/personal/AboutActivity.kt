package com.micropole.sxwine.module.personal

import android.content.pm.PackageManager
import com.blankj.utilcode.util.Utils
import com.example.baseframe.BaseActivity
import com.micropole.sxwine.R
import com.micropole.sxwine.base.initToolBar
import kotlinx.android.synthetic.main.activity_about.*
import kotlinx.android.synthetic.main.item_toolbar.*


/**
 * Created by JohnnyH on 2018/6/15.
 * 关于我们
 */
class AboutActivity : BaseActivity() {
    private  var mVersionName : String =""
    override fun getLayoutId(): Int = R.layout.activity_about

    override fun initView() {
        initToolBar(getString(R.string.tv_about))
        toolbar.setNavigationOnClickListener { finish() }
        try {
            val packageInfo = Utils.getApp()
                    .getPackageManager()
                    .getPackageInfo(Utils.getApp().getPackageName(), 0)
            mVersionName = packageInfo.versionName

        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        tv_versions.text=mVersionName
    }

    override fun initListener() {

    }

    override fun initData() {

    }
}