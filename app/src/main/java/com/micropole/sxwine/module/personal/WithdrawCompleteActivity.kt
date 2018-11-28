package com.micropole.sxwine.module.personal

import com.example.baseframe.BaseActivity
import com.micropole.sxwine.R
import com.micropole.sxwine.base.initToolBar
import kotlinx.android.synthetic.main.item_toolbar.*

/**
 * Created by JohnnyH on 2018/6/14.
 */
class WithdrawCompleteActivity : BaseActivity() {

    override fun getLayoutId(): Int = R.layout.activity_withdraw_complete

    override fun initView() {
        initToolBar(getString(R.string.withdraw_complete))
        toolbar.setNavigationOnClickListener { finish() }
    }

    override fun initListener() {

    }

    override fun initData() {

    }
}