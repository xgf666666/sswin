package com.micropole.sxwine.module.personal

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.example.mvpframe.BaseMvpActivity
import com.micropole.sxwine.R
import com.micropole.sxwine.base.hideLoadingDialog
import com.micropole.sxwine.base.initToolBar
import com.micropole.sxwine.base.showLoadingDialog
import com.micropole.sxwine.base.toast
import com.micropole.sxwine.module.personal.Bean.WithdrawListEntity
import com.micropole.sxwine.module.personal.adapter.WithdrawListAdapter
import com.micropole.sxwine.module.personal.mvp.contract.WithdrawListContract
import com.micropole.sxwine.module.personal.mvp.presenter.WithdrawListPresenter
import kotlinx.android.synthetic.main.activity_withdraw_list.*
import kotlinx.android.synthetic.main.item_toolbar.*
import kotlinx.android.synthetic.main.layout_error_view.*

/**
 * Created by JohnnyH on 2018/6/14.
 * 提现列表
 */
class WithdrawListActivity : BaseMvpActivity<WithdrawListContract.Model,WithdrawListContract.View,WithdrawListPresenter>(),WithdrawListContract.View {

    private lateinit var mAdapter : WithdrawListAdapter
    private var currentPage : Int=1

    override fun createPresenter(): WithdrawListPresenter = WithdrawListPresenter()
    override fun getLayoutId(): Int = R.layout.activity_withdraw_list

    override fun initView() {
        initToolBar(getString(R.string.withdraw_list))
        toolbar.setNavigationOnClickListener { finish() }
        recyclerView.layoutManager = LinearLayoutManager(mContext)
        mAdapter=WithdrawListAdapter(R.layout.item_withdraw_list_time,null)
        recyclerView.adapter=mAdapter
        mAdapter.setOnLoadMoreListener {
            loadData(false,false)
        }
    }

    override fun initData() {
        showLoadingDialog()
        loadData(true,true)
    }

    override fun initListener() {
        swipeRefreshLayout.setOnRefreshListener {
            currentPage=1
            loadData(true,false)
        }
        view_error.setOnClickListener {
            currentPage=1
            showLoadingDialog()
            loadData(true,true)
        }
    }

    private fun loadData(isRefresh : Boolean,isFirstLoading: Boolean){
        mPresenter.loadData(isRefresh,isFirstLoading,currentPage.toString())
    }

    override fun setData(data: MutableList<WithdrawListEntity>?) {
        currentPage++
        hideLoadingDialog()
        view_error.visibility= View.GONE
        swipeRefreshLayout.isRefreshing=false
        mAdapter.setNewData(data)
    }

    override fun addData(data: MutableList<WithdrawListEntity>?) {
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
            view_error.visibility=View.VISIBLE
        }
        hideLoadingDialog()
        swipeRefreshLayout.isRefreshing=false
        mAdapter.loadMoreFail()
        toast(err!!)
    }

}