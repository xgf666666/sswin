package com.micropole.sxwine.module.home

import android.app.Dialog
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.view.View
import android.widget.AdapterView
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.mvpframe.BaseMvpActivity
import com.micropole.sxwine.R
import com.micropole.sxwine.R.id.*
import com.micropole.sxwine.adapter.GoodsAdapter
import com.micropole.sxwine.base.hideLoadingDialog
import com.micropole.sxwine.base.initToolBar
import com.micropole.sxwine.base.showLoadingDialog
import com.micropole.sxwine.base.toast
import com.micropole.sxwine.bean.GoodsBean
import com.micropole.sxwine.module.home.mvp.contract.GoodsContract
import com.micropole.sxwine.module.home.mvp.presenter.GoodsPresenter
import kotlinx.android.synthetic.main.activity_goods.*
import kotlinx.android.synthetic.main.item_goods_header.*

class GoodsActivity : BaseMvpActivity<GoodsContract.Model, GoodsContract.View, GoodsPresenter>(), GoodsContract.View, View.OnClickListener {

    companion object {
        private val PRICE: String = "mall_price"
        private val SOLD: String = "sold_count"
        private val DESC: String = "desc"
        private val ASC: String = "asc"
    }

    private lateinit var mGoodsList: ArrayList<GoodsBean>
    private lateinit var mGoodsAdapter: GoodsAdapter

    private lateinit var mCategory: String
    private lateinit var mSort: String
    private lateinit var mDirection: String
    private var mPage: Int = 1

    override fun createPresenter(): GoodsPresenter = GoodsPresenter()

    override fun getLayoutId(): Int = R.layout.activity_goods

    override fun initView() {
        initToolBar(getBundle()?.getString("title")!!)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        mGoodsList = ArrayList()
        mGoodsAdapter = GoodsAdapter(R.layout.item_rcv_goods, mGoodsList)
        mGoodsAdapter.addHeaderView(getHeader())
        mGoodsAdapter.setOnLoadMoreListener({
            mPresenter.getGoods(mCategory, (++mPage), mSort, mDirection)
        }, rcv_goods)
        mGoodsAdapter.setOnItemClickListener { _, _, position ->
            val bundle = Bundle()
            if ("" == mGoodsList[position].video_url) {
                bundle.putInt("id", mGoodsList[position].goods_id.toInt())
                startActivity(GoodDetailActivity::class.java, bundle)
            } else {
                bundle.putString("url", mGoodsList[position].video_url)
                bundle.putString("title", mGoodsList[position].goods_name)
                bundle.putString("img", mGoodsList[position].cover_img)
                startActivity(VideoActivity::class.java, bundle)
            }
        }
        rcv_goods.adapter = mGoodsAdapter
    }

    private fun getHeader(): View {
        val view = layoutInflater.inflate(R.layout.item_goods_header, null, false)
        view.findViewById<ImageView>(R.id.price_up).setOnClickListener(this)
        view.findViewById<ImageView>(R.id.price_up).isSelected = true
        view.findViewById<ImageView>(R.id.price_down).setOnClickListener(this)
        view.findViewById<ImageView>(R.id.sale_up).setOnClickListener(this)
        view.findViewById<ImageView>(R.id.sale_down).setOnClickListener(this)
        return view
    }

    override fun initListener() {
        val mSwipeRefreshLayout = findViewById<SwipeRefreshLayout>(R.id.mSwipeRefreshLayout)
        mSwipeRefreshLayout.setOnRefreshListener {
            mPage = 1
            showLoadingDialog()
            mPresenter.getGoods(mCategory, mPage, mSort, mDirection)
            mSwipeRefreshLayout.isRefreshing = false
        }
    }

    override fun initData() {
        mCategory = getBundle()?.getString("category")!!
        mPage = 1
        mSort = PRICE
        mDirection = ASC
        showLoadingDialog()
        mPresenter.getGoods(mCategory, mPage, mSort, mDirection)
    }

    private fun selectSort(v: View) {
        resetSort()
        v.isSelected = true
        when (v) {
            price_up -> {
                mSort = PRICE
                mDirection = ASC
            }
            price_down -> {
                mSort = PRICE
                mDirection = DESC
            }
            sale_up -> {
                mSort = SOLD
                mDirection = ASC
            }
            sale_down -> {
                mSort = SOLD
                mDirection = DESC
            }
        }
        showLoadingDialog()
        mPage = 1
        mPresenter.getGoods(mCategory, mPage, mSort, mDirection)
    }

    private fun resetSort() {
        price_up.isSelected = false
        price_down.isSelected = false
        sale_up.isSelected = false
        sale_down.isSelected = false
    }

    override fun onSuccess(list: ArrayList<GoodsBean>, msg: String) {
        if (mPage > 1) {
            if (list.size > 0) {
                mGoodsList.addAll(list)
                mGoodsAdapter.setNewData(mGoodsList)
                mGoodsAdapter.loadMoreComplete()
            } else {
                mGoodsAdapter.loadMoreEnd()
                mPage--
            }
        } else {
            mGoodsList.clear()
            mGoodsList.addAll(list)
            mGoodsAdapter.setNewData(mGoodsList)
            hideLoadingDialog()
        }
    }

    override fun onFailure(err: String) {
        hideLoadingDialog()
        toast(err)
        if (mPage > 1) {
            mGoodsAdapter.loadMoreEnd()
            mPage--
        }
    }

    override fun onClick(v: View) {
        when (v) {
            price_up -> selectSort(v)
            price_down -> selectSort(v)
            sale_up -> selectSort(v)
            sale_down -> selectSort(v)
        }
    }

}
