package com.micropole.sxwine.module.personal

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.example.mvpframe.BaseMvpActivity
import com.micropole.sxwine.R
import com.micropole.sxwine.base.hideLoadingDialog
import com.micropole.sxwine.base.initToolBar
import com.micropole.sxwine.base.showLoadingDialog
import com.micropole.sxwine.base.toast
import com.micropole.sxwine.module.personal.Bean.OrganizationEarningsEntity
import com.micropole.sxwine.module.personal.adapter.OrganizationEarningsAdapter
import com.micropole.sxwine.module.personal.mvp.contract.OrganizationEarningsContract
import com.micropole.sxwine.module.personal.mvp.presenter.OrganizationEarningsPresenter
import kotlinx.android.synthetic.main.item_toolbar.*
import kotlinx.android.synthetic.main.layout_error_view.*
import kotlinx.android.synthetic.main.organizeation_earinings.*

/**
 * Created by JohnnyH on 2018/6/14.
 * 收益详情
 */
class OrganizationEarningsActivity : BaseMvpActivity<OrganizationEarningsContract.Model,OrganizationEarningsContract.View,OrganizationEarningsPresenter>(),OrganizationEarningsContract.View{

    private lateinit var mAdapter : OrganizationEarningsAdapter
    private var currentPage : Int=1
    private lateinit var mType : String

    override fun createPresenter(): OrganizationEarningsPresenter = OrganizationEarningsPresenter()
    override fun getLayoutId(): Int = R.layout.organizeation_earinings

    override fun initView() {
        initToolBar(getString(R.string.organization_earnings_detials))
        toolbar.setNavigationOnClickListener { finish() }
        recyclerView.layoutManager=LinearLayoutManager(mContext)
        mAdapter=OrganizationEarningsAdapter(R.layout.item_organization_earnings,null)
        recyclerView.adapter=mAdapter
        mAdapter.setOnLoadMoreListener {
            loadData(false,false)
        }
    }

    override fun initData() {
        showLoadingDialog()
        mType=intent.getStringExtra("type")
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
        mPresenter.loadData(isRefresh,isFirstLoading,currentPage.toString(),"10",mType)
    }

    override fun setData(data: MutableList<OrganizationEarningsEntity>?) {
        currentPage++
        hideLoadingDialog()
        view_error.visibility= View.GONE
        swipeRefreshLayout.isRefreshing=false
        mAdapter.setNewData(data)

    }

    override fun addData(data: MutableList<OrganizationEarningsEntity>?) {
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