package com.micropole.sxwine.module.home

import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import com.example.mvpframe.BaseMvpActivity
import com.micropole.sxwine.R
import com.micropole.sxwine.adapter.CommendAdapter
import com.micropole.sxwine.base.initToolBar
import com.micropole.sxwine.base.toast
import com.micropole.sxwine.bean.GoodDetailBean
import com.micropole.sxwine.module.home.mvp.contract.CommendListContract
import com.micropole.sxwine.module.home.mvp.presenter.CommendListPresenter

class CommendListActivity : BaseMvpActivity<CommendListContract.Model, CommendListContract.View, CommendListPresenter>(), CommendListContract.View {

    override fun createPresenter(): CommendListPresenter = CommendListPresenter()

    override fun getLayoutId(): Int = R.layout.activity_commend_list

    private var mPage = 0
    private var mId = 0
    private var isEnd = false
    private lateinit var mSwipeRefreshLayout: SwipeRefreshLayout

    private lateinit var rcv_comment: RecyclerView
    private val mCommentList = ArrayList<GoodDetailBean.Comment>()
    private val mCommentAdaptor by lazy {
        CommendAdapter(this, R.layout.rcv_comment)
    }

    override fun initView() {
        initToolBar(getString(R.string.all_evaluate))
        initSwipeRefreshLayout()
        initRecyclerView()
    }

    private fun initSwipeRefreshLayout() {
        mSwipeRefreshLayout = findViewById(R.id.mRefreshLayout)
        mSwipeRefreshLayout.setOnRefreshListener {
            mPage = 0
            mPresenter.getAllCommend(mId, mPage)
        }
    }

    private fun initRecyclerView() {
        rcv_comment = findViewById(R.id.rcv_goods_commend)
        mCommentAdaptor.setOnLoadMoreListener({
            if (!isEnd) {
                mPage += 1
                mPresenter.getAllCommend(mId, mPage)
            }
        }, rcv_comment)
        rcv_comment.adapter = mCommentAdaptor
    }

    override fun initListener() {
    }

    override fun initData() {
        mId = getBundle()!!.getInt("id")
        mPresenter.getAllCommend(mId, mPage)
    }

    override fun onSuccess(list: ArrayList<GoodDetailBean.Comment>, msg: String) {
        mSwipeRefreshLayout.isRefreshing = false
        if (mPage == 0) {
            mCommentList.clear()
        }
        if (list.size > 0) {
            mCommentList.addAll(list)
            mCommentAdaptor.setNewData(mCommentList)
        } else {
            isEnd = true
            mCommentAdaptor.loadMoreEnd()
        }
    }

    override fun onFailure(error: String) {
        toast(error)
    }
}
