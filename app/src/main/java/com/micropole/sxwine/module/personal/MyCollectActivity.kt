package com.micropole.sxwine.module.personal

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.example.mvpframe.BaseMvpActivity
import com.micropole.sxwine.R
import com.micropole.sxwine.base.hideLoadingDialog
import com.micropole.sxwine.base.initToolBar
import com.micropole.sxwine.base.showLoadingDialog
import com.micropole.sxwine.base.toast
import com.micropole.sxwine.module.home.GoodDetailActivity
import com.micropole.sxwine.module.personal.Bean.MyCollectEntity
import com.micropole.sxwine.module.personal.adapter.MyCollectAdapter
import com.micropole.sxwine.module.personal.mvp.contract.MyCollectContract
import com.micropole.sxwine.module.personal.mvp.presenter.MyCollectPresenter
import kotlinx.android.synthetic.main.activity_my_collect.*
import kotlinx.android.synthetic.main.item_toolbar.*
import kotlinx.android.synthetic.main.layout_error_view.*

/**
 * Created by JohnnyH on 2018/6/12.
 */
class MyCollectActivity : BaseMvpActivity<MyCollectContract.Model,MyCollectContract.View,MyCollectPresenter>(),MyCollectContract.View {

    private var currentPage : Int = 1
    private lateinit var mAdapter : MyCollectAdapter

    override fun createPresenter(): MyCollectPresenter = MyCollectPresenter()
    override fun getLayoutId(): Int = R.layout.activity_my_collect

    override fun initView() {
        initToolBar(getString(R.string.my_collect))
        toolbar.setNavigationOnClickListener {
            finish()
        }
        recyclerView.layoutManager=LinearLayoutManager(mContext)
        mAdapter= MyCollectAdapter(R.layout.item_my_colloect,null)
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
        recyclerView.addOnItemTouchListener(object : OnItemClickListener(){
            override fun onSimpleItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                val bundle = Bundle()
                bundle.putInt("id",mAdapter.data[position].goods_id.toInt())
                startActivity(GoodDetailActivity::class.java,bundle)
            }

        })
    }

    private fun loadData(isRefresh : Boolean,isFirstLoading : Boolean){
        mPresenter.loadData(isRefresh,isFirstLoading,currentPage.toString(),"10")
    }

    override fun setData(data: MutableList<MyCollectEntity>?) {
        hideLoadingDialog()
        view_error.visibility=View.GONE
        currentPage++
        swipeRefreshLayout.isRefreshing=false
        mAdapter.setNewData(data)
    }

    override fun addData(data: MutableList<MyCollectEntity>?) {
        currentPage++
        if (data!=null&&!data.isEmpty()){
            mAdapter.loadMoreComplete()
            mAdapter.addData(data)
        }else {
            mAdapter.loadMoreEnd()
        }
    }

    override fun onDataFailure(err: String?, isFirstLoading: Boolean) {
        toast(err!!)
        hideLoadingDialog()
        if (isFirstLoading){
            view_error.visibility= View.VISIBLE
        }
        mAdapter.loadMoreFail()
        swipeRefreshLayout.isRefreshing=false
    }
}