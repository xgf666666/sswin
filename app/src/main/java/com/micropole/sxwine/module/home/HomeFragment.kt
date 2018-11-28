package com.micropole.sxwine.module.home

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.opengl.Visibility
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.AbsListView
import android.widget.ScrollView
import com.bigkoo.convenientbanner.ConvenientBanner
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.mvpframe.BaseMvpFragment
import com.micropole.sxwine.R
import com.micropole.sxwine.adapter.VipGoodsAdapter
import com.micropole.sxwine.adapter.GoodsAdapter
import com.micropole.sxwine.adapter.BannerHolder
import com.micropole.sxwine.adapter.ClassifyAdapter
import com.micropole.sxwine.base.hideLoadingDialog
import com.micropole.sxwine.bean.*
import com.micropole.sxwine.module.home.mvp.contract.HomeContract
import com.micropole.sxwine.module.home.mvp.presenter.HomePresenter
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.item_home_header.*
import kotlinx.android.synthetic.main.item_home_header.view.*
import android.support.v7.widget.LinearLayoutManager
import com.blankj.ALog.i
import com.darkhorse.preferencesmanager.PreferencesHelper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.micropole.tanglong.WebViewActivity
import com.micropole.sxwine.R.id.ll_search
import com.micropole.sxwine.R.id.ll_search2
import com.micropole.sxwine.base.showLoadingDialog

/**
 * Description:
 * Created by DarkHorse on 2018/5/30.
 */
class HomeFragment : BaseMvpFragment<HomeContract.Model, HomeContract.View, HomePresenter>(), HomeContract.View, View.OnClickListener {


    private lateinit var mBanner: ConvenientBanner<BannerBean>
    private lateinit var mSwipeRefreshLayout: SwipeRefreshLayout
//    private lateinit var mMarqueeView: MarqueeView

    private lateinit var mClassifyList: ArrayList<ClassifyBean>
    private lateinit var mClassifyAdapter: ClassifyAdapter

    private lateinit var mGoodsList: ArrayList<GoodsBean>
    private lateinit var mGoodsAdapter: GoodsAdapter

    private lateinit var mVipGoodsList: ArrayList<VipGoodsBean>
    private lateinit var mVipGoodsAdapter: VipGoodsAdapter

    private val mBannerList: ArrayList<BannerBean> = ArrayList()

    private var mPage: Int = 1

    override fun getLayoutId(): Int = R.layout.fragment_home

    override fun initView(rootView: View) {
        rootView.ll_search2.setOnClickListener(this)
        rootView.iv_massage_2.setOnClickListener(this)
        initRecyclerView(rootView)
        initSwipeRefreshLayout(rootView)
    }

    private fun initSwipeRefreshLayout(rootView: View) {
        mSwipeRefreshLayout = rootView.findViewById(R.id.mSwipeRefreshLayout)
        mSwipeRefreshLayout.setOnRefreshListener {
            initData()
        }
    }

    private fun initRecyclerView(rootView: View) {
        mGoodsList = ArrayList()
        mGoodsAdapter = GoodsAdapter(R.layout.item_rcv_goods, mGoodsList)
        mGoodsAdapter.addHeaderView(getHeader())
        mGoodsAdapter.setOnLoadMoreListener({
            mPresenter.getGoods(++mPage)
        }, rootView.rcv_goods)
        mGoodsAdapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener { _, _, position ->
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
        rootView.rcv_goods.adapter = mGoodsAdapter
        rootView.rcv_goods.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val distance = getScrollYDistance(rootView.rcv_goods)
                if (distance < ll_search2.height) {
                    ll_search2.alpha = distance.toFloat() / ll_search2.height
                } else {
                    if (ll_search2.alpha != 0f) {
                        ll_search2.alpha = 1f
                    }
                }
            }
        })
    }

    fun getScrollYDistance(view: RecyclerView): Int {
        val layoutManager = view.layoutManager as LinearLayoutManager
        val position = layoutManager.findFirstVisibleItemPosition()
        val firstVisiableChildView = layoutManager.findViewByPosition(position)
        val itemHeight = firstVisiableChildView.height
        return position * itemHeight - firstVisiableChildView.top
    }

    @SuppressLint("InflateParams")
    private fun getHeader(): View {
        val rootView = layoutInflater.inflate(R.layout.item_home_header, null, false)
//        mMarqueeView = rootView.findViewById(R.id.tv_activity)
        initBanner(rootView)
        mClassifyList = ArrayList()
        mClassifyAdapter = ClassifyAdapter(R.layout.item_rcv_classify, mClassifyList)
        mClassifyAdapter.setOnItemClickListener { _, _, position ->
            val bean = mClassifyList[position]
            val bundle = Bundle()
            bundle.putString("title", bean.name)
            bundle.putString("category", bean.category_id.toString())
            startActivity(GoodsActivity::class.java, bundle)
        }
        rootView.tv_all_classify.setOnClickListener { startActivity(MoreClassifyActivity::class.java) }
        rootView.rcv_classify.adapter = mClassifyAdapter
        rootView.rcv_classify.isNestedScrollingEnabled = false

        rootView.rcv_activity.layoutManager = GridLayoutManager(mContext, 2)
        mVipGoodsList = ArrayList()
        mVipGoodsAdapter = VipGoodsAdapter(R.layout.item_rcv_activity, mVipGoodsList)
        mVipGoodsAdapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener { _, _, position ->
            val bundle = Bundle()
            if ("" == mVipGoodsList[position].video_url) {
                bundle.putInt("id", mVipGoodsList[position].goods_id.toInt())
                bundle.putBoolean("vip", true)
                startActivity(GoodDetailActivity::class.java, bundle)
            } else {
                bundle.putString("url", mVipGoodsList[position].video_url)
                bundle.putString("title", mVipGoodsList[position].goods_name)
                bundle.putString("img", mVipGoodsList[position].cover_img)
                startActivity(VideoActivity::class.java, bundle)
            }
        }
        rootView.rcv_activity.adapter = mVipGoodsAdapter
        rootView.rcv_activity.isNestedScrollingEnabled = false
        rootView.ll_search.setOnClickListener(this)
        rootView.iv_massage_1.setOnClickListener(this)
        return rootView
    }

    private fun initBanner(rootView: View) {
        mBanner = rootView.findViewById<ConvenientBanner<BannerBean>>(R.id.mBanner)

        val dm = resources.displayMetrics
        val width = dm.widthPixels
        val param = mBanner.layoutParams
        param.height = width / 16 * 9
        mBanner.layoutParams = param

        mBanner.setPages({
            BannerHolder()
        }, mBannerList)
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setPageIndicator(intArrayOf(R.drawable.round_banner_off, R.drawable.round_banner_on))
                .startTurning(3000)
                .setOnItemClickListener { position: Int ->
                    val type = mBannerList[position].type_id
                    if (type == "2") {
                        val bundle = Bundle()
                        bundle.putInt("id", mBannerList[position].goods_id.toInt())
                        startActivity(GoodDetailActivity::class.java, bundle)
                    } else if (type == "1") {
                        val intent = Intent(mActivity, WebViewActivity::class.java)
                        intent.putExtra(WebViewActivity.EXTRA_WEB_TYPE, WebViewActivity.TYPE_URL)
                        intent.putExtra(WebViewActivity.EXTRA_WEB_URL, mBannerList[position].link)
                        mActivity.startActivity(intent)
                    } else if (type == "3") {
                        val intent = Intent(mActivity, WebViewActivity::class.java)
                        intent.putExtra(WebViewActivity.EXTRA_WEB_TYPE, WebViewActivity.TYPE_CONTENT)
                        intent.putExtra(WebViewActivity.EXTRA_WEB_CONTENT, mBannerList[position].introduction)
                        mActivity.startActivity(intent)
                    }
                }

    }

    override fun initListener(rootView: View) {
    }

    override fun initData() {
        val bannerJson = PreferencesHelper.get("banner", "") as String
        val classifyJson = PreferencesHelper.get("classify", "") as String
        val goodsJson = PreferencesHelper.get("goods", "") as String
        val gson = Gson()

        if (bannerJson != "") {
            mBannerList.clear()
            mBannerList.addAll(gson.fromJson(bannerJson, object : TypeToken<ArrayList<BannerBean>>() {}.type))
            mBanner.notifyDataSetChanged()
        }

        if (classifyJson != "") {
            mClassifyList.clear()
            mClassifyList.addAll(gson.fromJson(classifyJson, object : TypeToken<ArrayList<ClassifyBean>>() {}.type))
            mClassifyAdapter.setNewData(mClassifyList)
        }

        if (goodsJson != "") {
            mGoodsList.clear()
            mGoodsList.addAll(gson.fromJson(goodsJson, object : TypeToken<ArrayList<GoodsBean>>() {}.type))
            mGoodsAdapter.setNewData(mGoodsList)
        }

        startLoading()
        mPage = 1
        mPresenter.getBanner()
        mPresenter.getClassify()
        mPresenter.getGoods(mPage)
    }

    override fun createPresenter(): HomePresenter = HomePresenter()

    override fun onBannerSuccess(list: ArrayList<BannerBean>, msg: String) {
        PreferencesHelper.put("bunner", list)
        mBannerList.clear()
        for (bean in list) {
            mBannerList.add(bean)
        }
        mBanner.notifyDataSetChanged()
    }

    override fun onBannerFailure(err: String) {
        toast(err)
    }

    override fun onClassifySuccess(list: ArrayList<ClassifyBean>, msg: String) {
        val classifyList = ArrayList<ClassifyBean>()
        for (i in 0..2) {
            classifyList.add(list[i])
        }
        PreferencesHelper.put("classify", classifyList)
        mClassifyList.clear()
        mClassifyList.addAll(classifyList)
        mClassifyAdapter.setNewData(mClassifyList)
    }

    override fun onClassifyFailure(err: String) {
        toast(err)
    }

    override fun onGoodsSuccess(list: HomeResult, msg: String) {
        if (mPage > 1) {
            if (list.goods.size > 0) {
                mGoodsList.addAll(list.goods)
                mGoodsAdapter.setNewData(mGoodsList)
                mClassifyAdapter.loadMoreComplete()
            } else {
                mPage--
                mGoodsAdapter.loadMoreEnd()
            }
        } else {
            PreferencesHelper.put("goods", list.goods)

            val acitivities = ArrayList<String>()
            for (text in list.activity) {
                acitivities.add(text.title)
            }
//            mMarqueeView.startWithList(acitivities)

            mVipGoodsList.clear()
            if (list.vip_goods.size >= 2) {
                if (list.vip_goods[0].vip_level == "1") {
                    mVipGoodsList.add(list.vip_goods[0])
                    mVipGoodsList.add(list.vip_goods[1])
                } else {
                    mVipGoodsList.add(list.vip_goods[1])
                    mVipGoodsList.add(list.vip_goods[0])
                }
            }

            mVipGoodsAdapter.setNewData(mVipGoodsList)

            mGoodsList.clear()
            mGoodsList.addAll(list.goods)
            mGoodsAdapter.setNewData(mGoodsList)
            mActivity.hideLoadingDialog()
        }

    }

    override fun onGoodsFailure(err: String) {
        if (mPage > 1) {
            mGoodsAdapter.loadMoreEnd()
            mPage--
        } else {
            mActivity.hideLoadingDialog()
        }
        toast(err)
    }

    private fun startLoading() {
        mActivity.showLoadingDialog()
        mSwipeRefreshLayout.isRefreshing = false
    }

    override fun onClick(v: View?) {
        when (v) {
            ll_search -> startActivity(SearchActivity::class.java)
            ll_search2 -> startActivity(SearchActivity::class.java)
            iv_massage_2 -> startActivity(MessageActivity::class.java)
            iv_massage_1 -> startActivity(MessageActivity::class.java)
        }
    }
}
