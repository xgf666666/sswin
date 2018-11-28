package com.micropole.sxwine.module.personal

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.darkhorse.preferencesmanager.PreferencesHelper
import com.example.mvpframe.BaseMvpFragment
import com.micropole.sxwine.R
import com.micropole.sxwine.base.hideLoadingDialog
import com.micropole.sxwine.base.showLoadingDialog

import com.micropole.sxwine.module.order.PayActivity
import com.micropole.sxwine.module.personal.Bean.MyOrderItemEntity
import com.micropole.sxwine.module.personal.adapter.MyOrderAdapter
import com.micropole.sxwine.module.personal.mvp.contract.MyOrderContract
import com.micropole.sxwine.module.personal.mvp.presenter.MyOrderPresenter
import kotlinx.android.synthetic.main.fragment_my_order.*
import kotlinx.android.synthetic.main.fragment_my_order.view.*
import kotlinx.android.synthetic.main.layout_error_view.*
import kotlinx.android.synthetic.main.layout_error_view.view.*

/**
 * Created by JohnnyH on 2018/6/15.
 * 我的订单
 */
class MyOrderFragment : BaseMvpFragment<MyOrderContract.Model, MyOrderContract.View, MyOrderPresenter>(), MyOrderContract.View {

    private lateinit var mAdapter: MyOrderAdapter
    private var currentPage: Int = 1
    private var status: Int = -2
    private lateinit var mRootView: View

    override fun createPresenter(): MyOrderPresenter = MyOrderPresenter()
    override fun getLayoutId(): Int = R.layout.fragment_my_order

    override fun initView(rootView: View) {
        mRootView = rootView
        mRootView.recyclerView.layoutManager = LinearLayoutManager(mContext)
        mAdapter = MyOrderAdapter(null)
        mRootView.recyclerView.adapter = mAdapter
        mAdapter.setOnLoadMoreListener {
            //加载更多
            loadData(false, false)
        }
        mAdapter.setOnCountdownEndingListener(object : MyOrderAdapter.OnCountdownEndingListener {
            override fun countdownEnding() {
                currentPage = 1
                loadData(true, false)
            }

        })
    }

    override fun onResume() {
        super.onResume()
        currentPage = 1
        loadData(true, true)
    }

    override fun handlerArguments(arguments: Bundle?) {
        status = arguments!!.getInt("status")
    }

    override fun initData() {
        mActivity.showLoadingDialog()
        currentPage = 1
        loadData(true, true)
    }

    override fun initListener(rootView: View) {
        mRootView.swipeRefreshLayout.setOnRefreshListener {
            currentPage = 1
            loadData(true, false)
        }
        mRootView.view_error.setOnClickListener {
            currentPage = 1
            mActivity.showLoadingDialog()
            loadData(true, true)
        }
        mRootView.recyclerView.addOnItemTouchListener(object : OnItemClickListener() {
            override fun onSimpleItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                val intent = Intent(mContext, OrderDetailsActivity::class.java)
                intent.putExtra("order_id", mAdapter.data[position].order_id)
                startActivityForResult(intent, 0)
            }

        })
        mRootView.recyclerView.addOnItemTouchListener(object : OnItemChildClickListener() {
            override fun onSimpleItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                when (view!!.id) {
                    R.id.btn_pay -> {//去付款
                        val bundle = Bundle()
                        bundle.putString("order_id",mAdapter.data[position].order_id)
                        bundle.putString("pay_amount",mAdapter.data[position].pay_amount)
                        val intent = Intent(mContext, PayActivity::class.java)
                        intent.putExtra("data",bundle)
                        startActivity(intent)
                    }
                    R.id.btn_confirm_receive -> {//确认收货
                        mActivity.showLoadingDialog()
                        PreferencesHelper
                        mPresenter.confirmReceive(mAdapter.data[position].order_id)
                    }
                    R.id.btn_cancel -> {//取消订单
                        mActivity.showLoadingDialog()
                        mPresenter.cancelOrder(mAdapter.data[position].order_id)
                    }
                    R.id.btn_comment->{//评价
                        val intent = Intent(mContext, CommentGoodsListActivity::class.java)
                        intent.putExtra("order_id",mAdapter.data[position].order_id)
                        startActivity(intent)
                    }
                    R.id.btn_delete -> {//删除订单
                        mActivity.showLoadingDialog()
                        mPresenter.deleteOrder(mAdapter.data[position].order_id)
                    }
                }
            }
        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        currentPage = 1
        loadData(true, false)
    }

    private fun loadData(isRefresh: Boolean, isFirstLoading: Boolean) {
        mPresenter.loadData(isRefresh, isFirstLoading, currentPage.toString(), "10", status.toString())
    }

    override fun setData(data: MutableList<MyOrderItemEntity>?) {
        currentPage++
        swipeRefreshLayout.isRefreshing = false
        view_error.visibility = View.GONE
        mActivity.hideLoadingDialog()
        if (!data!!.isEmpty()) {
            mAdapter.setNewData(data)
        } else {
            mAdapter.setNewData(null)
            mAdapter.emptyView = getEmptyView()
        }

    }

    private fun getEmptyView(): View? {
        return View.inflate(mContext, R.layout.layout_empty_view, null);
    }

    override fun addData(data: MutableList<MyOrderItemEntity>?) {
        currentPage++
        if (data != null && !data.isEmpty()) {
            mAdapter.loadMoreComplete()
            mAdapter.addData(data)
        } else {
            mAdapter.loadMoreEnd()
        }
    }

    override fun onDataFailure(err: String?, isFirstLoading: Boolean) {
        if (isFirstLoading) {
            view_error.visibility = View.VISIBLE
        }
        mActivity.hideLoadingDialog()
        swipeRefreshLayout.isRefreshing = false
        mAdapter.loadMoreFail()
        toast(err!!)
    }

    override fun onDeleteOrderSuccess() {
        mActivity.hideLoadingDialog()
        currentPage = 1
        loadData(true, false)
    }

    override fun onDeleteOrderFailure(err: String?) {
        mActivity.hideLoadingDialog()
        toast(err!!)
    }

    override fun onConfirmReceiveSuccess() {
        mActivity.hideLoadingDialog()
        currentPage = 1
        loadData(true, false)
    }

    override fun onConfirmReceiveFailure(err: String?) {
        mActivity.hideLoadingDialog()
        toast(err!!)
    }

    override fun onCancelOrderSuccess() {
        mActivity.hideLoadingDialog()
        currentPage = 1
        loadData(true, false)
    }

    override fun onCancelOrderFailure(err: String?) {
        mActivity.hideLoadingDialog()
        toast(err!!)
    }
}