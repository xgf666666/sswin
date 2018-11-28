package com.micropole.sxwine.module.personal

import android.support.v4.app.Fragment
import com.example.baseframe.BaseActivity
import com.micropole.sxwine.R
import com.micropole.sxwine.base.initToolBar
import com.micropole.sxwine.module.personal.adapter.OrderFragmentAdapter
import kotlinx.android.synthetic.main.activity_my_order.*
import kotlinx.android.synthetic.main.item_toolbar.*

/**
 * Created by JohnnyH on 2018/6/15.
 * 我的订单
 */
class MyOrderActivity : BaseActivity() {

    private  var mIndex : Int =0

    override fun getLayoutId(): Int = R.layout.activity_my_order

    override fun initView() {
        initToolBar(getString(R.string.tv_my_order))
        toolbar.setNavigationOnClickListener { finish() }
    }

    override fun initListener() {

    }

    override fun initData() {
        mIndex=intent.getIntExtra("index",0)
        val fragments = ArrayList<Fragment>()
        val titles = arrayOf(getString(R.string.all_order), getString(R.string.tv_wait_pay), getString(R.string.tv_wait_send), getString(R.string.tv_wait_receive),getString(R.string.tv_completed))
        for (i in titles.indices) {
            fragments.add(MyOrderFragment())
        }
        viewPager.adapter= OrderFragmentAdapter(supportFragmentManager, fragments, titles)
        slidingTabLayout.setViewPager(viewPager)
        viewPager.currentItem = mIndex
    }
}