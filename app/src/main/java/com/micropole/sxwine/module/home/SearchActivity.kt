package com.micropole.sxwine.module.home

import android.content.Context
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.AbsListView
import android.widget.ImageView
import android.widget.TextView
import com.example.mvpframe.BaseMvpActivity
import com.micropole.sxwine.R
import com.micropole.sxwine.R.id.*
import com.micropole.sxwine.adapter.GoodsAdapter
import com.micropole.sxwine.base.hideLoadingDialog
import com.micropole.sxwine.base.showLoadingDialog
import com.micropole.sxwine.bean.GoodsBean
import com.micropole.sxwine.module.home.mvp.contract.SearchContract
import com.micropole.sxwine.module.home.mvp.presenter.SearchPresenter
import kotlinx.android.synthetic.main.activity_goods.*
import kotlinx.android.synthetic.main.item_goods_header.*
import kotlinx.android.synthetic.main.item_toolbar.*


class SearchActivity : BaseMvpActivity<SearchContract.Model, SearchContract.View, SearchPresenter>(), SearchContract.View, View.OnClickListener {

    companion object {
        private val PRICE: String = "mall_price"
        private val SOLD: String = "sold_count"
        private val DESC: String = "desc"
        private val ASC: String = "asc"
    }

    private lateinit var mSort: String
    private lateinit var mDirection: String
    private var mPage: Int = 1

    private lateinit var imm: InputMethodManager

    private lateinit var mGoodsList: ArrayList<GoodsBean>
    private lateinit var mGoodsAdapter: GoodsAdapter

    override fun createPresenter(): SearchPresenter = SearchPresenter()

    override fun getLayoutId(): Int = R.layout.activity_search

    override fun onResume() {
        super.onResume()
    }

    override fun initView() {
        toolbar.setNavigationOnClickListener { onBackPressed() }
        initRecyclerView()
    }

    private fun initRecyclerView() {
        mGoodsList = ArrayList()
        mGoodsAdapter = GoodsAdapter(R.layout.item_rcv_goods, mGoodsList)
        mGoodsAdapter.addHeaderView(getHeader())
        mGoodsAdapter.setOnLoadMoreListener({
            mPresenter.searchGoods(toolbar_title.text.toString(), (++mPage), mSort, mDirection)
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
        rcv_goods.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                imm.hideSoftInputFromWindow(toolbar_title.getWindowToken(), 0);
            }
        })
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
        toolbar_title.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (!toolbar_title.text.isEmpty()) {
                        showLoadingDialog()
                        mPage = 1
                        mPresenter.searchGoods(toolbar_title.text.toString(), mPage, mSort, mDirection)
                    } else {
                        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                        imm.hideSoftInputFromWindow(findViewById<View>(R.id.toolbar_title).windowToken, 0);
                    }
                }
                return false
            }
        })

        val mSwipeRefreshLayout = findViewById<SwipeRefreshLayout>(R.id.mSwipeRefreshLayout)
        mSwipeRefreshLayout.setOnRefreshListener {
            if (!toolbar_title.text.isEmpty()) {
                showLoadingDialog()
                mPage = 1
                mPresenter.searchGoods(toolbar_title.text.toString(), mPage, mSort, mDirection)
            } else {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(findViewById<View>(R.id.toolbar_title).windowToken, 0);
            }
            mSwipeRefreshLayout.isRefreshing = false
        }
    }

    override fun initData() {
        imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        mPage = 1
        mSort = PRICE
        mDirection = ASC
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
        mPresenter.searchGoods(toolbar_title.text.toString(), mPage, mSort, mDirection)
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
