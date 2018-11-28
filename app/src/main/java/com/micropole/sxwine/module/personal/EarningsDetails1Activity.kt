package com.micropole.sxwine.module.personal

import android.support.v7.widget.LinearLayoutManager
import com.example.mvpframe.BaseMvpActivity
import com.micropole.sxwine.R
import com.micropole.sxwine.base.hideLoadingDialog
import com.micropole.sxwine.base.initToolBar
import com.micropole.sxwine.base.showLoadingDialog
import com.micropole.sxwine.base.toast
import com.micropole.sxwine.module.personal.Bean.AccumulateEarningsEntity
import com.micropole.sxwine.module.personal.Bean.EarningsDetails1Entity
import com.micropole.sxwine.module.personal.Bean.EarningsDetails1ItemEnitity
import com.micropole.sxwine.module.personal.adapter.EarningsDetails1Adapter
import com.micropole.sxwine.module.personal.mvp.contract.EarningDetails1Contract
import com.micropole.sxwine.module.personal.mvp.presenter.EarningsDetails1Presenter
import kotlinx.android.synthetic.main.activity_earnings_detials1.*
import kotlinx.android.synthetic.main.item_toolbar.*

/**
 * Created by JohnnyH on 2018/6/13.
 * 今天,昨天,累积收益详情
 */
class EarningsDetails1Activity : BaseMvpActivity<EarningDetails1Contract.Model,EarningDetails1Contract.View,EarningsDetails1Presenter>(),EarningDetails1Contract.View {

    private lateinit var mAdapter : EarningsDetails1Adapter
    private lateinit var mType : String

    override fun createPresenter(): EarningsDetails1Presenter = EarningsDetails1Presenter()

    override fun getLayoutId(): Int = R.layout.activity_earnings_detials1

    override fun initView() {
        mType = intent.getStringExtra("type")
        initToolBar(intent.getStringExtra("title"))
        toolbar.setNavigationOnClickListener { finish() }
        recyclerView.layoutManager=LinearLayoutManager(mContext)
        mAdapter=EarningsDetails1Adapter(R.layout.item_earnings_detials1,null)
        recyclerView.adapter=mAdapter
    }

    override fun initListener() {

    }

    override fun initData() {
        showLoadingDialog()
        if ("3"!=mType){//1:今日收益2:昨天收益3:累积收益
            mPresenter.loadData(mType)
        }else{
            mPresenter.getAccumulateEarnings()
        }

    }

    override fun setData(data: EarningsDetails1Entity?) {
        hideLoadingDialog()
        mAdapter.setNewData(createList(data))
    }

    override fun setAccumulateEarnings(data: AccumulateEarningsEntity?) {
        hideLoadingDialog()
        mAdapter.setNewData(createList2(data))
    }

    override fun onDataFailure(err: String?) {
        hideLoadingDialog()
        toast(err!!)
    }

    private fun createList(data: EarningsDetails1Entity?) : List<EarningsDetails1ItemEnitity>{
        var itemList : ArrayList<EarningsDetails1ItemEnitity> = ArrayList()
        itemList.add(EarningsDetails1ItemEnitity(getString(R.string.award_1),data!!.directly_amount))
        itemList.add(EarningsDetails1ItemEnitity(getString(R.string.award_2),data!!.form_amount))
        itemList.add(EarningsDetails1ItemEnitity(getString(R.string.award_3),data!!.repeat_amount))
        return itemList
    }
    private fun createList2(data: AccumulateEarningsEntity?) : List<EarningsDetails1ItemEnitity>{
        var itemList : ArrayList<EarningsDetails1ItemEnitity> = ArrayList()
        itemList.add(EarningsDetails1ItemEnitity(getString(R.string.award_1),data!!.directly_amount_total))
        itemList.add(EarningsDetails1ItemEnitity(getString(R.string.award_2),data!!.organization_amount_total))
        itemList.add(EarningsDetails1ItemEnitity(getString(R.string.award_3),data!!.consume_amount_total))
        return itemList
    }
}