package com.micropole.sxwine.module.personal

import android.content.Intent
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
import com.micropole.sxwine.module.personal.Bean.FQAEntity
import com.micropole.sxwine.module.personal.adapter.FQAAdapter
import com.micropole.sxwine.module.personal.mvp.contract.FQAContract
import com.micropole.sxwine.module.personal.mvp.presenter.FQAPresenter
import kotlinx.android.synthetic.main.activity_fqa.*
import kotlinx.android.synthetic.main.item_toolbar.*
import kotlinx.android.synthetic.main.layout_error_view.*

/**
 * Created by JohnnyH on 2018/6/12.
 * 常见问题
 */
class FQAActivity : BaseMvpActivity<FQAContract.Model,FQAContract.View,FQAPresenter>(),FQAContract.View {

    private lateinit var mAdapter : FQAAdapter

    override fun createPresenter(): FQAPresenter = FQAPresenter()

    override fun getLayoutId(): Int = R.layout.activity_fqa

    override fun initView() {
        initToolBar(getString(R.string.tv_issue))
        toolbar.setNavigationOnClickListener {finish()}
        recyclerView.layoutManager=LinearLayoutManager(mContext)
        mAdapter=FQAAdapter(R.layout.item_fqa,null)
        recyclerView.adapter=mAdapter
    }

    override fun initListener() {
        swipeRefreshLayout.setOnRefreshListener {
            loadData(true,false)
        }
        recyclerView.addOnItemTouchListener(object : OnItemClickListener(){
            override fun onSimpleItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                val intent = Intent(mContext, AnswerActivity::class.java)
                intent.putExtra("answer",mAdapter.data[position].answer)
                startActivity(intent)
            }

        })
        view_error.setOnClickListener {
            showLoadingDialog()
            loadData(true,true)
        }
    }

    override fun initData() {
        showLoadingDialog()
        loadData(true,true)
    }

    private fun loadData(isRefresh : Boolean,isFirstLoading : Boolean){
        mPresenter.loadData(isRefresh,isFirstLoading)
    }

    override fun setData(data: MutableList<FQAEntity>?) {
        view_error.visibility=View.GONE
        swipeRefreshLayout.isRefreshing=false
        hideLoadingDialog()
        mAdapter.setNewData(data)
    }

    override fun onDataFailure(err: String?, isFirstLoading: Boolean) {
        toast(err!!)
        view_error.visibility=View.VISIBLE
        hideLoadingDialog()
    }
}