package com.micropole.sxwine.module.order

import com.example.baseframe.BaseActivity
import com.micropole.sxwine.MainActivity
import com.micropole.sxwine.R
import com.micropole.sxwine.base.initToolBar
import kotlinx.android.synthetic.main.activity_complete_pay.*
import kotlinx.android.synthetic.main.item_toolbar.*

class CompletePay : BaseActivity() {
    override fun getLayoutId(): Int = R.layout.activity_complete_pay

    override fun initView() {
        initToolBar(getString(R.string.success_pay))
        toolbar.navigationIcon = null
    }

    override fun initListener() {
        btn_back.setOnClickListener {
            startActivity(MainActivity::class.java)
        }
    }

    override fun initData() {
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(MainActivity::class.java)
    }

}
