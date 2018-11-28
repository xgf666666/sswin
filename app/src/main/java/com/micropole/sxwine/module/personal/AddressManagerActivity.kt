package com.micropole.sxwine.module.personal

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.example.mvpframe.BaseMvpActivity
import com.micropole.sxwine.R
import com.micropole.sxwine.base.hideLoadingDialog
import com.micropole.sxwine.base.initToolBar
import com.micropole.sxwine.base.showLoadingDialog
import com.micropole.sxwine.base.toast
import com.micropole.sxwine.module.personal.Bean.AddressManagerEntity
import com.micropole.sxwine.module.personal.adapter.AddressManagerAdapter
import com.micropole.sxwine.module.personal.mvp.contract.AddressManagerContract
import com.micropole.sxwine.module.personal.mvp.presenter.AddressManagerPresenter
import kotlinx.android.synthetic.main.activity_address_manager.*
import kotlinx.android.synthetic.main.item_toolbar.*
import kotlinx.android.synthetic.main.layout_error_view.*

/**
 * Created by JohnnyH on 2018/6/8.
 */
class AddressManagerActivity : BaseMvpActivity<AddressManagerContract.Model, AddressManagerContract.View, AddressManagerPresenter>(), AddressManagerContract.View, View.OnClickListener {

    private var currentPage: Int = 1
    private var isFirstLoad: Boolean = true

    private lateinit var mAdapter: AddressManagerAdapter

    override fun createPresenter(): AddressManagerPresenter = AddressManagerPresenter()

    override fun getLayoutId(): Int = R.layout.activity_address_manager

    override fun initView() {
        initToolBar(getString(R.string.manager_address))
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        view_error.setOnClickListener(this)
        btn_add.setOnClickListener(this)
        recyclerView.layoutManager = LinearLayoutManager(mContext)
        mAdapter = AddressManagerAdapter(R.layout.item_address_manager, null)
        mAdapter.addFooterView(View.inflate(mContext,R.layout.view_address_manager_foot,null))
        recyclerView.adapter = mAdapter
        mAdapter.setOnLoadMoreListener {
            isFirstLoad = false
            loadData(false)
        }
    }

    override fun initData() {
        showLoadingDialog()
        loadData(true)
    }

    override fun initListener() {
        swipeRefreshLayout.setOnRefreshListener {
            isFirstLoad = false
            currentPage = 1
            loadData(true)
        }
        recyclerView.addOnItemTouchListener(object : OnItemClickListener() {
            override fun onSimpleItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                if (getBundle() != null && getBundle()!!.getBoolean("isOrder")) {
                    intent.putExtra("address", mAdapter.data[position])
                    setResult(123, intent)
                    finish()
                } else {
                    val addressManagerEntity = mAdapter.data[position]
                    val intent = Intent(mContext, AddAddressActivity::class.java)
                    intent.putExtra("AddressManagerEntity", addressManagerEntity)
                    intent.putExtra("type", "2")//1: 直接添加 2:编辑
                    intent.putExtra("title", getString(R.string.compile_address))

                    startActivityForResult(intent, 0)
                }
            }
        })

        recyclerView.addOnItemTouchListener(object : OnItemChildClickListener(){
            override fun onSimpleItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                when(view!!.id){
                    R.id.iv_pen->{//编辑
                        val addressManagerEntity = mAdapter.data[position]
                        val intent = Intent(mContext, AddAddressActivity::class.java)
                        intent.putExtra("AddressManagerEntity", addressManagerEntity)
                        intent.putExtra("type", "2")//1: 直接添加 2:编辑
                        intent.putExtra("title", getString(R.string.compile_address))
                        startActivityForResult(intent, 0)
                    }
                    R.id.ll_delete->{//删除
                        showLoadingDialog()
                        mPresenter.deleteAddress(mAdapter.data[position].address_id)
                    }
                }

            }

        })
    }

    private fun loadData(isRefresh: Boolean) {
        mPresenter.loadData(isRefresh, currentPage.toString(), "10")
    }

    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.view_error -> {//加载错误布局
                currentPage = 1
                showLoadingDialog()
                loadData(true)
            }
            R.id.btn_add -> {//添加新地址
                val intent = Intent(mContext, AddAddressActivity::class.java)
                intent.putExtra("title", getString(R.string.add_address))
                intent.putExtra("type", "1")//1: 直接添加 2:编辑
                intent.putExtra("is_default", "0")
                startActivityForResult(intent, 0)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        currentPage = 1
        mPresenter.loadData(true, currentPage.toString(), "10")
    }

    override fun setData(data: MutableList<AddressManagerEntity>?) {
        currentPage++
        view_error.visibility = View.GONE
        swipeRefreshLayout.isRefreshing = false
        hideLoadingDialog()
        mAdapter.setNewData(data)
    }

    override fun addData(data: MutableList<AddressManagerEntity>?) {
        currentPage++
        if (data != null && !data.isEmpty()) {
            mAdapter.loadMoreComplete()
            mAdapter.addData(data)
        } else {
            mAdapter.loadMoreEnd()
        }
    }

    override fun onDataFailure(isRefresh: Boolean, err: String?) {
        toast(err!!)
        mAdapter.loadMoreFail()
        hideLoadingDialog()
        swipeRefreshLayout.isRefreshing = false
        if (isFirstLoad) {
            view_error.visibility = View.VISIBLE
        }
    }

    override fun onDeleteAddressSuccess() {
        hideLoadingDialog()
        currentPage = 1
        mPresenter.loadData(true, currentPage.toString(), "10")
    }

    override fun onDeleteAddressFailure(err: String?) {
        hideLoadingDialog()
        toast(err!!)
    }
}