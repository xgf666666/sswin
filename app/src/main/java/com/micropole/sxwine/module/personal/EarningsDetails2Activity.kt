package com.micropole.sxwine.module.personal

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.example.mvpframe.BaseMvpActivity
import com.micropole.sxwine.R
import com.micropole.sxwine.base.hideLoadingDialog
import com.micropole.sxwine.base.initToolBar
import com.micropole.sxwine.base.showLoadingDialog
import com.micropole.sxwine.base.toast
import com.micropole.sxwine.module.personal.Bean.EarningsDetails2Entity
import com.micropole.sxwine.module.personal.adapter.EarningsDetails2TimeAdapter
import com.micropole.sxwine.module.personal.mvp.contract.EarningsDetails2Contract
import com.micropole.sxwine.module.personal.mvp.presenter.EarningsDetails2Presenter
import kotlinx.android.synthetic.main.activity_earnings_detials2.*
import kotlinx.android.synthetic.main.item_toolbar.*
import kotlinx.android.synthetic.main.layout_error_view.*

/**
 * Created by JohnnyH on 2018/6/13.
 * 年度,季度,收益详情
 */
class EarningsDetails2Activity : BaseMvpActivity<EarningsDetails2Contract.Model,EarningsDetails2Contract.View,EarningsDetails2Presenter>(),EarningsDetails2Contract.View {

    private lateinit var mType : String
    private var currentPage=1
    private lateinit var mAdapter : EarningsDetails2TimeAdapter

    override fun createPresenter(): EarningsDetails2Presenter = EarningsDetails2Presenter()
    override fun getLayoutId(): Int = R.layout.activity_earnings_detials2

    override fun initView() {
        mType = intent.getStringExtra("type")
        if ("2"==mType){//2:年度1:季度
            initToolBar(getString(R.string.year_earnings))
        }else{
            initToolBar(getString(R.string.quarter_earnings))
        }
        toolbar.setNavigationOnClickListener { finish() }
        recyclerView.layoutManager=LinearLayoutManager(mContext)
        mAdapter= EarningsDetails2TimeAdapter(R.layout.item_earnings_detials2_time,null)
        recyclerView.adapter=mAdapter
        mAdapter.setOnLoadMoreListener {
            loadData(false,false)
        }
    }

    override fun initListener() {
        swipeRefreshLayout.setOnRefreshListener {
            currentPage=1
            loadData(true,false)
        }

        view_error.setOnClickListener{
            currentPage=1
            showLoadingDialog()
            loadData(true,true)
        }
    }

    override fun initData() {
        showLoadingDialog()
        loadData(true,true)
    }

    private fun loadData(isRefresh : Boolean,isFirstLoading: Boolean){
        mPresenter.loadData(isRefresh,isFirstLoading,mType,currentPage.toString(),"10")
    }

    override fun setData(data: MutableList<EarningsDetails2Entity>?) {
        currentPage++
        view_error.visibility= View.GONE
        swipeRefreshLayout.isRefreshing=false
        hideLoadingDialog()
        mAdapter.setNewData(data)

    }

    override fun addData(data: MutableList<EarningsDetails2Entity>?) {
        currentPage++
        if (data!=null&&!data.isEmpty()){
            mAdapter.loadMoreComplete()
            mAdapter.addData(data)
        }else{
            mAdapter.loadMoreEnd()
        }
    }

    override fun onDataFailure(err: String?, isFirstLoading: Boolean) {
        if (isFirstLoading){
            view_error.visibility= View.VISIBLE
        }
        hideLoadingDialog()
        mAdapter.loadMoreFail()
        swipeRefreshLayout.isRefreshing=false
        toast(err!!)
    }
}