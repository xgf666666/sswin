package com.micropole.sxwine.module.home

import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import com.example.mvpframe.BaseMvpActivity
import com.micropole.tanglong.WebViewActivity
import com.micropole.sxwine.R
import com.micropole.sxwine.R.id.rcv_message
import com.micropole.sxwine.adapter.MessageAdapter
import com.micropole.sxwine.base.initToolBar
import com.micropole.sxwine.base.toast
import com.micropole.sxwine.bean.MessageBean
import com.micropole.sxwine.module.home.mvp.contract.MessageContract
import com.micropole.sxwine.module.home.mvp.presenter.MessagePresenter
import kotlinx.android.synthetic.main.activity_message.*

/**
 * Description:
 * Created by DarkHorse on 2018/9/6.
 */
class MessageActivity : BaseMvpActivity<MessageContract.Model, MessageContract.View, MessagePresenter>(), MessageContract.View {

    override fun createPresenter(): MessagePresenter = MessagePresenter()

    override fun getLayoutId(): Int = R.layout.activity_message

    private lateinit var mSwipeRefreshLayout: SwipeRefreshLayout

    private var mPage = 0

    private val mMessageList = ArrayList<MessageBean>()
    private val mAdapter by lazy {
        MessageAdapter(this, R.layout.rcv_item_message)
    }

    override fun initView() {
        initToolBar(getString(R.string.message))
        initRefreshLayout()
        initRecyclerView()
    }

    private fun initRefreshLayout() {
        mSwipeRefreshLayout = mRefreshLayout
        mSwipeRefreshLayout.setOnRefreshListener {
            mPage = 0
            mPresenter.getMessageList(mPage)
        }
    }

    private fun initRecyclerView() {
        mAdapter.setOnItemClickListener { adapter, view, position ->
            val type = mMessageList[position].type
            if (type == "2") {
                val bundle = Bundle()
                bundle.putInt("id", mMessageList[position].goodsId.toInt())
                startActivity(GoodDetailActivity::class.java, bundle)
            } else if (type == "1") {
                val intent = Intent(this, WebViewActivity::class.java)
                intent.putExtra(WebViewActivity.EXTRA_WEB_TYPE, WebViewActivity.TYPE_URL)
                intent.putExtra(WebViewActivity.EXTRA_WEB_URL, mMessageList[position].link)
                startActivity(intent)
            } else if (type == "3") {
                val intent = Intent(this, WebViewActivity::class.java)
                intent.putExtra(WebViewActivity.EXTRA_WEB_TYPE, WebViewActivity.TYPE_CONTENT)
                intent.putExtra(WebViewActivity.EXTRA_WEB_CONTENT, mMessageList[position].content)
                startActivity(intent)
            }
        }
        mAdapter.setOnLoadMoreListener({
            mPage += 1
            mPresenter.getMessageList(mPage)
        }, rcv_message)
        rcv_message.adapter = mAdapter
    }

    override fun initListener() {
    }

    override fun initData() {
        mPresenter.getMessageList(mPage)
    }

    override fun onSuccess(list: ArrayList<MessageBean>, msg: String) {
        if (mPage == 0) {
            mMessageList.clear()
            mMessageList.addAll(list)
            if (mMessageList.size > 0) {
                mAdapter.setNewData(mMessageList)
            } else {
                mAdapter.setEmptyView(R.layout.layout_empty_view)
            }
        } else {
            if (list.size > 0) {
                mMessageList.addAll(list)
                mAdapter.setNewData(mMessageList)
            } else {
                mAdapter.loadMoreEnd()
            }
        }
        mSwipeRefreshLayout.isRefreshing = false
    }

    override fun onFailure(error: String) {
        toast(error)
        mSwipeRefreshLayout.isRefreshing = false
    }

}
