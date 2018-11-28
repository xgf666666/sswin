package com.micropole.sxwine.module.personal

import com.example.baseframe.BaseActivity
import com.micropole.sxwine.R
import com.micropole.sxwine.R.id.*
import com.micropole.sxwine.base.initToolBar
import com.micropole.sxwine.base.loadImg
import kotlinx.android.synthetic.main.activity_vip_detail.*

class VipDetail : BaseActivity() {
    override fun getLayoutId(): Int = R.layout.activity_vip_detail

    override fun initView() {
        initToolBar(getString(R.string.vip_detail))
    }

    override fun initListener() {
    }

    override fun initData() {
        iv_icon.loadImg(this, getBundle()!!.getString("ic_launcher"))
        tv_name.text = getBundle()!!.getString("name")
        tv_vip.text = getBundle()!!.getString("vip")
        tv_account.text = getBundle()!!.getString("id")
    }

}
