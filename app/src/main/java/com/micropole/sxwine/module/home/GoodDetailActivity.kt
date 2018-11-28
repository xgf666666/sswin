package com.micropole.sxwine.module.home

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.WindowManager
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.LinearLayout
import com.bigkoo.convenientbanner.ConvenientBanner
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator
import com.example.mvpframe.BaseMvpActivity
import com.micropole.sxwine.adapter.BannerHolder2
import com.micropole.sxwine.bean.GoodDetailBean
import com.micropole.sxwine.bean.SettleResult
import com.micropole.sxwine.module.home.mvp.contract.GoodDetailContract
import com.micropole.sxwine.module.home.mvp.presenter.GoodDetailPresenter
import com.micropole.sxwine.module.order.ConfirmActivity
import com.micropole.sxwine.utils.ViewPagerActivity
import kotlinx.android.synthetic.main.activity_good_detail.*
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.darkhorse.viewindicator.IViewClickListener
import com.example.baseframe.permission.PermissionCode
import com.micropole.sxwine.R
import com.micropole.sxwine.R.id.*
import com.micropole.sxwine.R.layout.rcv_comment
import com.micropole.sxwine.adapter.CommendAdapter
import com.micropole.sxwine.adapter.GoodsAdapter
import com.micropole.sxwine.base.*
import com.micropole.sxwine.bean.GoodsBean
import com.micropole.sxwine.utils.network.API
import com.micropole.sxwine.widgets.MarqueeView
import kotlinx.android.synthetic.main.item_purchase_dialog.view.*
import pub.devrel.easypermissions.AfterPermissionGranted
import java.util.ResourceBundle.getBundle

class GoodDetailActivity : BaseMvpActivity<GoodDetailContract.Model, GoodDetailContract.View, GoodDetailPresenter>(), GoodDetailContract.View, View.OnClickListener {

    private val mBannerList: ArrayList<String> = ArrayList()
    private lateinit var mBanner: ConvenientBanner<String>

    private var mCount = 1
    private lateinit var mIntroduction: String
    private lateinit var mPrice: String
    private lateinit var mIconUrl: String

    private val mBottomDialog: AlertDialog by lazy {
        val dialog = AlertDialog.Builder(mContext).show()

        val contentView = layoutInflater.inflate(R.layout.item_purchase_dialog, null)
        contentView.tv_introduction.text = mIntroduction
        contentView.iv_icon.loadImg(mContext, API.HOST + mIconUrl, R.mipmap.zhaoshao)
        contentView.tv_price.text = "RMB $mPrice"
        contentView.iv_close.setOnClickListener(this)
        contentView.btn_confirm.setOnClickListener(this)
        val mAmountView = contentView.mAmountView
        mAmountView.setGoods_storage(Int.MAX_VALUE)
        mAmountView.setOnAmountChangeListener { _, amount ->
            mCount = amount
        }

        val window = dialog.window
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        window.setBackgroundDrawableResource(android.R.color.transparent)
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        window.setGravity(Gravity.BOTTOM)//底部弹出
        window.setWindowAnimations(R.style.Animation_Bottom_Rising)

        window.setContentView(contentView)

        dialog
    }

    private var mId: Int = 0
    private var isPurchase = false

    private lateinit var rcv_comment: RecyclerView
    private val mCommentList = ArrayList<GoodDetailBean.Comment>()
    private val mCommentAdaptor by lazy {
        CommendAdapter(this, R.layout.rcv_comment)
    }

    private lateinit var rcv_recommend: RecyclerView
    private val mRecommendList = ArrayList<GoodsBean>()
    private val mRecommendAdaptor by lazy {
        GoodsAdapter(R.layout.item_rcv_goods, mRecommendList)
    }

    override fun createPresenter(): GoodDetailPresenter = GoodDetailPresenter()

    override fun getLayoutId(): Int = R.layout.activity_good_detail

    override fun initView() {
        initToolBar(getString(R.string.goods_details))
        initIndicator()
        initBanner()
        initWebView()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        rcv_recommend = findViewById(R.id.rcv_goods_recommend)
        mRecommendAdaptor.setOnItemClickListener { adapter, view, position ->
            val bundle = Bundle()
            if ("" == mRecommendList[position].video_url) {
                bundle.putInt("id", mRecommendList[position].goods_id.toInt())
                startActivity(GoodDetailActivity::class.java, bundle)
            } else {
                bundle.putString("url", mRecommendList[position].video_url)
                bundle.putString("title", mRecommendList[position].goods_name)
                bundle.putString("img", mRecommendList[position].cover_img)
                startActivity(VideoActivity::class.java, bundle)
            }
        }
        rcv_recommend.adapter = mRecommendAdaptor

        rcv_comment = findViewById(R.id.rcv_goods_commend)
        mCommentAdaptor.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
            val bundle = Bundle()
            bundle.putInt("id", mId)
            startActivity(CommendListActivity::class.java, bundle)
        }
        rcv_comment.adapter = mCommentAdaptor
    }

    private fun initIndicator() {
        mIndicator.init(arrayOf(getString(com.micropole.sxwine.R.string.goods_details_details), getString(com.micropole.sxwine.R.string.goods_details_comment), getString(R.string.goods_details_recommend)), object : IViewClickListener {
            override fun onViewClickListener(position: Int) {
                when (position) {
                    0 -> {
                        wb_detail.visibility = View.VISIBLE
                        rcv_comment.visibility = View.GONE
                        rcv_recommend.visibility = View.GONE
                    }
                    1 -> {
                        wb_detail.visibility = View.GONE
                        rcv_comment.visibility = View.VISIBLE
                        rcv_recommend.visibility = View.GONE
                    }
                    2 -> {
                        wb_detail.visibility = View.GONE
                        rcv_comment.visibility = View.GONE
                        rcv_recommend.visibility = View.VISIBLE
                    }
                }
            }
        })
    }

    private fun initWebView() {
        val settings = wb_detail.settings
        wb_detail.webChromeClient = WebChromeClient();
        wb_detail.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                imgReset()
                super.onPageFinished(view, url)
            }
        };
        settings.javaScriptEnabled = true
        settings.setSupportZoom(false)
    }

    private fun initBanner() {
        mBanner = findViewById<ConvenientBanner<String>>(R.id.mBanner)

        val dm = resources.displayMetrics
        val width = dm.widthPixels
        val param = mBanner.layoutParams
        param.height = width
        mBanner.layoutParams = param

        mBanner.setPages(CBViewHolderCreator {
            BannerHolder2()
        }, mBannerList)
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setPageIndicator(intArrayOf(R.drawable.round_banner_off, R.drawable.round_banner_on))
                .startTurning(3000)
        mBanner.setOnItemClickListener { position ->
            val intent = Intent(mContext, ViewPagerActivity::class.java).apply {
                putExtra("imgUrl_index", position)
                putExtra("imgUrls", mBannerList.toTypedArray())
            }
            startActivity(intent)
        }
    }

    override fun initListener() {
        iv_back.setOnClickListener(this)
//        tv_car.setOnClickListener(this)
//        tv_service.setOnClickListener(this)
        tv_collection.setOnClickListener(this)
        tv_purchase.setOnClickListener(this)
        tv_add_car.setOnClickListener(this)
    }

    override fun initData() {
        mId = getBundle()?.getInt("id")!!
        showLoadingDialog()
        mPresenter.getData(mId)
    }

    override fun onSuccess(bean: GoodDetailBean, msg: String) {
        hideLoadingDialog()
        mBannerList.clear()
        if (bean.goodsImg.isNotEmpty()) {
            mBannerList.addAll(bean.goodsImg)
        }
        mBanner.notifyDataSetChanged()

        mIntroduction = bean.goodsName
        mPrice = bean.mallPrice
        mIconUrl = bean.thumb_img

        tv_collection.isSelected = bean.collect == "1"

        tv_text.text = mIntroduction
        tv_price_1.text = "RMB $mPrice"
        tv_price_2.text = "${resources.getString(R.string.shoppe_price)} RMB ${bean.shopPrice}"
        tv_sale.text = (bean.soldCount + getString(R.string.hadSale))

        wb_detail.loadDataWithBaseURL(null, bean.decp, "text/html", "utf-8", null)

        mRecommendList.clear()
        for (r in bean.recommendGoods) {
            mRecommendList.add(GoodsBean(r.goodsId, r.goodsName, r.coverImg, r.goodsImg, r.mallPrice, r.shopPrice, r.introduce, r.decp, r.soldCount, r.commentCount, r.isHot, "", "", "", "",""))
        }
        if (mRecommendList.isEmpty()) {
            mRecommendAdaptor.setEmptyView(R.layout.layout_empty_view, rcv_recommend)
        } else {
            mRecommendAdaptor.setNewData(mRecommendList)
        }

        mCommentList.clear()
        mCommentList.addAll(bean.comments)
        if (mCommentList.isEmpty()) {
            mCommentAdaptor.setEmptyView(R.layout.layout_empty_view, rcv_comment)
        } else {
            mCommentAdaptor.setNewData(mCommentList)
        }
    }

    override fun onFailure(err: String) {
        hideLoadingDialog()
        toast(err)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.iv_close -> mBottomDialog.hide()
            R.id.btn_confirm -> {
                if (isPurchase) {
                    showLoadingDialog()
                    mPresenter.buyGoods(mId.toString(), mCount.toString())
                } else {
                    showLoadingDialog()
                    mPresenter.addCar(mId, mCount)
                    mBottomDialog.dismiss()
                }
            }
            R.id.iv_back -> onBackPressed()
//            R.id.tv_car -> {
//                val bundle = Bundle()
//                bundle.putBoolean("isLogin", false)
//                startActivity(MainActivity::class.java, bundle)
//            }
            R.id.tv_purchase -> {
                isPurchase = true
                mBottomDialog.show()
            }
            R.id.tv_add_car -> {
                isPurchase = false
                val isVip = getBundle()?.getBoolean("vip")
                if (isVip != null && isVip == true) {
                    toast(getString(R.string.vip_car_hint))
                } else {
                    mBottomDialog.show()
                }
            }
            R.id.tv_collection -> {
                if (tv_collection.isSelected) {
                    mPresenter.cancelGoods(mId)
                    showLoadingDialog()
                } else {
                    mPresenter.collectGoods(mId)
                    showLoadingDialog()
                }
            }
//            R.id.tv_service -> {
//                startActivity(ContactServiceActivity::class.java)
//            }
        }
    }

    override fun addSuccess(msg: String) {
        toast(msg)
        hideLoadingDialog()
    }

    override fun addFailure(err: String) {
        toast(err)
        hideLoadingDialog()
    }

    override fun collectSuccess(msg: String) {
        tv_collection.isSelected = true
        toast(msg)
        hideLoadingDialog()
    }

    override fun collectFailure(err: String) {
        toast(err)
        hideLoadingDialog()
    }

    override fun cancelSuccess(msg: String) {
        tv_collection.isSelected = false
        toast(msg)
        hideLoadingDialog()
    }

    override fun cancelFailure(err: String) {
        toast(err)
        hideLoadingDialog()
    }

    /**
     * 对图片进行重置大小，宽度就是手机屏幕宽度，高度根据宽度比便自动缩放
     */
    private fun imgReset() {
        wb_detail.loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName('img'); " +
                "for(var i=0;i<objs.length;i++)  " +
                "{"
                + "var img = objs[i];   " +
                "    img.style.maxWidth = '100%'; img.style.height = 'auto';  " +
                "}" +
                "})()")
    }

    override fun buySuccess(result: SettleResult, msg: String) {
        hideLoadingDialog()
        val bundle = Bundle()

        bundle.putSerializable("result", result)
        startActivity(ConfirmActivity::class.java, bundle)
    }

    override fun buyFailure(err: String) {
        hideLoadingDialog()
    }


}
