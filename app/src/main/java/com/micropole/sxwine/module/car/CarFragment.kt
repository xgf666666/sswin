package com.micropole.sxwine.module.car

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.view.View
import android.widget.CheckBox
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.mvpframe.BaseMvpFragment
import com.micropole.sxwine.R
import com.micropole.sxwine.R.id.*
import com.micropole.sxwine.adapter.CarGoodsAdapter
import com.micropole.sxwine.adapter.CarRecommendAdapter
import com.micropole.sxwine.adapter.GoodsAdapter
import com.micropole.sxwine.base.hideLoadingDialog
import com.micropole.sxwine.base.showLoadingDialog
import com.micropole.sxwine.bean.CarGoodsBean
import com.micropole.sxwine.bean.CarResult
import com.micropole.sxwine.bean.GoodsBean
import com.micropole.sxwine.bean.SettleResult
import com.micropole.sxwine.module.car.mvp.contract.CarContract
import com.micropole.sxwine.module.car.mvp.presenter.CarPresenter
import com.micropole.sxwine.module.home.GoodDetailActivity
import com.micropole.sxwine.module.home.VideoActivity
import com.micropole.sxwine.module.order.ConfirmActivity
import kotlinx.android.synthetic.main.fragment_car.*
import kotlinx.android.synthetic.main.fragment_car.view.*
import kotlinx.android.synthetic.main.item_toolbar.view.*
import java.text.DecimalFormat

class CarFragment : BaseMvpFragment<CarContract.Model, CarContract.View, CarPresenter>(), CarContract.View, View.OnClickListener {

    private lateinit var mCarGoodsAdapter: CarGoodsAdapter
    private lateinit var mCarGoodsList: ArrayList<CarGoodsBean>

    private lateinit var mCarRecommendAdapter: GoodsAdapter
    private lateinit var mCarRecommendList: ArrayList<GoodsBean>

    private lateinit var mAllCheck: CheckBox

    private var isEdit = false
    private var mPage: Int = 1

    private lateinit var mSwipeRefreshLayout: SwipeRefreshLayout

    override fun createPresenter(): CarPresenter = CarPresenter()

    override fun getLayoutId(): Int = R.layout.fragment_car

    override fun onResume() {
        super.onResume()
        mPresenter.getData(mPage)
    }

    override fun initView(rootView: View) {
        mAllCheck = rootView.cb_all
        mAllCheck.isChecked = false
        initToolbar(rootView)
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
        mCarGoodsList = ArrayList()
        mCarGoodsAdapter = CarGoodsAdapter(R.layout.item_rcv_car_goods, mCarGoodsList, ::isChoseAll, ::updateGoods)
        mCarGoodsAdapter.emptyView = layoutInflater.inflate(R.layout.item_rcv_not_data, null, false)
        rootView.rcv_car_goods.adapter = mCarGoodsAdapter

        mCarRecommendList = ArrayList()
        mCarRecommendAdapter = GoodsAdapter(R.layout.item_rcv_goods, mCarRecommendList)
        mCarRecommendAdapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener { _, _, position ->
            val bundle = Bundle()
            if ("" == mCarRecommendList[position].video_url) {
                bundle.putInt("id", mCarRecommendList[position].goods_id.toInt())
                startActivity(GoodDetailActivity::class.java, bundle)
            } else {
                bundle.putString("url", mCarRecommendList[position].video_url)
                bundle.putString("title", mCarRecommendList[position].goods_name)
                bundle.putString("img", mCarRecommendList[position].cover_img)
                startActivity(VideoActivity::class.java, bundle)
            }
        }
        rootView.rcv_car_recommend.adapter = mCarRecommendAdapter
    }

    private fun initToolbar(rootView: View) {
        rootView.toolbar_title.text = getString(R.string.home_tab_3)
        val toolbar = rootView.toolbar
        toolbar.inflateMenu(R.menu.menu_edit)
        toolbar.setOnMenuItemClickListener { it ->
            when (isEdit) {
                true -> {
                    it.title = getString(R.string.edit)
                    btn_goto_pay.text = getString(R.string.goto_pay)
                    sum.visibility = View.VISIBLE
                    tv_all.visibility = View.VISIBLE
                }
                false -> {
                    it.title = getString(R.string.save)
                    btn_goto_pay.text = getString(R.string.delete)
                    sum.visibility = View.GONE
                    tv_all.visibility = View.GONE
                }
            }
            isEdit = !isEdit
            true
        }
        toolbar.navigationIcon = null
    }

    override fun initListener(rootView: View) {
        rootView.btn_goto_pay.setOnClickListener(this)
        mAllCheck.setOnClickListener(this)

    }

    override fun initData() {
        startLoading()
        mPresenter.getData(mPage)
    }

    override fun onSuccess(list: ArrayList<CarGoodsBean>, recommends: ArrayList<GoodsBean>, msg: String) {
        if (mPage > 1) {
            if (list.size > 0) {
                mCarGoodsList.addAll(list)
                mCarGoodsAdapter.setNewData(mCarGoodsList)
            } else {
                mPage--
            }
        } else {
            mCarGoodsList.clear()
            mCarGoodsList.addAll(list)
            mCarGoodsAdapter.setNewData(mCarGoodsList)

            mCarRecommendList.clear()
            mCarRecommendList.addAll(recommends)
            mCarRecommendAdapter.setNewData(mCarRecommendList)
        }
        mActivity.hideLoadingDialog()
        mAllCheck.isChecked = false
        calculatePay()
    }

    override fun onFailure(err: String) {
        mActivity.hideLoadingDialog()
        toast(err)
        calculatePay()
    }

    override fun onClick(v: View?) {
        when (v) {
            btn_goto_pay -> {
                if (isEdit) {
                    val sb = StringBuilder()
                    for (i in mCarGoodsList) {
                        if (i.isChecked) {
                            sb.append(i.cartId + ",")
                        }
                    }
                    if (sb.length > 1) {
                        sb.subSequence(sb.length - 2, sb.length - 1)
                    }
                    val ids = sb.toString()
                    if ("" != ids) {
                        mActivity.showLoadingDialog()
                        mPresenter.deleteGoods(sb.toString())
                    }
                } else {
                    val sb = StringBuilder()
                    for (i in mCarGoodsList) {
                        if (i.isChecked) {
                            sb.append(i.cartId + ",")
                        }
                    }
                    if (sb.length > 2) {
                        sb.subSequence(sb.length - 2, sb.length - 1)
                    }
                    val ids = sb.toString()
                    if ("" != ids) {
                        mActivity.showLoadingDialog()
                        mPresenter.settleCart(sb.toString())
                    }
                }
            }
            cb_all -> {
                for (item in mCarGoodsList) {
                    item.isChecked = mAllCheck.isChecked
                }
                mCarGoodsAdapter.setNewData(mCarGoodsList)
            }
        }
    }

    private fun isChoseAll(isChoseAll: Boolean) {
        mAllCheck.isChecked = isChoseAll
        calculatePay()
    }

    private fun calculatePay() {
        var sum = 0.0
        for (bean in mCarGoodsList) {
            if (bean.isChecked) {
                sum += bean.quantity.toInt() * bean.goods.mall_price.toDouble()
            }
        }
        val str = DecimalFormat("0.00").format(sum)
        tv_all.text = "RMB $str "
    }

    private fun updateGoods(id: Int, quantity: Int) {
        mPresenter.updateGoods(id, quantity)
    }

    override fun addSuccess(msg: String) {
        toast(msg)
        mActivity.hideLoadingDialog()
    }

    override fun addFailure(err: String) {
        toast(err)
        mActivity.hideLoadingDialog()
    }

    override fun subSuccess(msg: String) {
        toast(msg)
        mActivity.hideLoadingDialog()
    }

    override fun subFailure(err: String) {
        toast(err)
        mActivity.hideLoadingDialog()
    }

    override fun settleSuccess(result: SettleResult, msg: String) {
        mActivity.hideLoadingDialog()
        val bundle = Bundle()

        bundle.putSerializable("result", result)
        startActivity(ConfirmActivity::class.java, bundle)
    }

    override fun settleFailure(err: String) {
        mActivity.hideLoadingDialog()
        toast(err)
    }

    override fun deleteSuccess(msg: String) {
        mPage = 1
        mPresenter.getData(mPage)
    }

    override fun deleteFailure(err: String) {
        toast(err)
        mPage = 1
        mPresenter.getData(mPage)
    }

    override fun updateSuccess(msg: String) {
        calculatePay()
    }

    override fun updateFailure(err: String) {
        calculatePay()
    }

    private fun startLoading() {
        mActivity.showLoadingDialog()
        mSwipeRefreshLayout.isRefreshing = false
    }
}
