package com.micropole.sxwine.module.personal

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.blankj.utilcode.util.TimeUtils
import com.example.mvpframe.BaseMvpActivity
import com.micropole.sxwine.R
import com.micropole.sxwine.base.hideLoadingDialog
import com.micropole.sxwine.base.initToolBar
import com.micropole.sxwine.base.showLoadingDialog
import com.micropole.sxwine.base.toast
import com.micropole.sxwine.module.order.PayActivity
import com.micropole.sxwine.module.personal.Bean.OrderDetailsEntity
import com.micropole.sxwine.module.personal.adapter.OrderDetallsAdapter
import com.micropole.sxwine.module.personal.mvp.contract.OrderDetailsContract
import com.micropole.sxwine.module.personal.mvp.presenter.OrderDetailsPresenter
import kotlinx.android.synthetic.main.activity_order_details.*
import kotlinx.android.synthetic.main.item_toolbar.*
import kotlinx.android.synthetic.main.layout_error_view.*
import kotlinx.android.synthetic.main.view_order_details_foot.view.*
import kotlinx.android.synthetic.main.view_order_details_head.view.*
import java.text.SimpleDateFormat

/**
 * Created by JohnnyH on 2018/6/19.
 * 订单详情
 */
class OrderDetailsActivity : BaseMvpActivity<OrderDetailsContract.Model,OrderDetailsContract.View,OrderDetailsPresenter>(),OrderDetailsContract.View, View.OnClickListener {

    private lateinit var mAdapter : OrderDetallsAdapter
    private lateinit var mOrder_id : String

    override fun createPresenter(): OrderDetailsPresenter = OrderDetailsPresenter()
    override fun getLayoutId(): Int = R.layout.activity_order_details

    override fun initView() {
        initToolBar(getString(R.string.tv_order_details))
        toolbar.setNavigationOnClickListener { finish() }
        mOrder_id=intent.getStringExtra("order_id")
        recyclerView.layoutManager=LinearLayoutManager(mContext)
        mAdapter= OrderDetallsAdapter(R.layout.item_order_goods,null)
        mAdapter.addHeaderView(getHeaderView())
        mAdapter.addFooterView(getFooterView())
        recyclerView.adapter=mAdapter
    }

    private lateinit var mHeaderView : View
    private fun getHeaderView(): View? {
        mHeaderView = View.inflate(mContext, R.layout.view_order_details_head, null)
        return mHeaderView
    }

    private lateinit var mFooterView : View
    private fun getFooterView(): View? {
        mFooterView = View.inflate(mContext,R.layout.view_order_details_foot,null)
        mFooterView.btn_1.setOnClickListener(this)
        mFooterView.btn_2.setOnClickListener(this)
        return mFooterView
    }

    override fun initData() {
        showLoadingDialog()
        loadData(true)
    }

    override fun initListener() {
        swipeRefreshLayout.setOnRefreshListener {
            loadData(false)
        }
        view_error.setOnClickListener{
            showLoadingDialog()
            loadData(true)
        }
    }

    private fun loadData(isFirstLoading : Boolean){
        mPresenter.loadData(isFirstLoading,mOrder_id)
    }

    override fun onClick(view: View?) {
        when(view!!.id){
            R.id.btn_1->{
                when(mData.status){
                    "1","2","3"->{//取消订单
                        showLoadingDialog()
                        mPresenter.cancelOrder(mOrder_id)
                    }
                    "8","4","-1"->{//删除订单
                        showLoadingDialog()
                        mPresenter.deleteOrder(mOrder_id)
                    }
                }
            }
            R.id.btn_2->{
                when(mData.status){
                    "1"->{//付款
                        val intent = Intent(mContext, PayActivity::class.java)
                        startActivity(intent)
                    }
                    "3"->{//确认收货
                        showLoadingDialog()
                        mPresenter.confirmReceive(mOrder_id)
                    }
                    "4"->{//已完成的去评价
                        val intent = Intent(mContext, CommentGoodsListActivity::class.java)
                        intent.putExtra("order_id",mData.order_id)
                        startActivity(intent)
                    }
                }
            }
        }
    }

    private lateinit var mData : OrderDetailsEntity
    override fun setData(data: OrderDetailsEntity?) {
        hideLoadingDialog()
        swipeRefreshLayout.isRefreshing=false
        view_error.visibility=View.GONE
        mData=data!!
        mAdapter.setNewData(mData.items)
        initHeaderView()
        initFooterView()

    }

    private fun initFooterView() {
        when(mData.status){
            "1"->{//待付款
                mFooterView.btn_2.text=getString(R.string.tv_btn_pay)
                mFooterView.btn_1.text=getString(R.string.cancel_order)
                mFooterView.btn_2.visibility=View.VISIBLE
                mFooterView.btn_1.visibility=View.VISIBLE
            }
            "2"->{//待发货
                mFooterView.btn_2.text=getString(R.string.tv_btn_pay)
                mFooterView.btn_1.text=getString(R.string.cancel_order)
                mFooterView.btn_2.visibility=View.GONE
                mFooterView.btn_1.visibility=View.VISIBLE
            }
            "3"->{//待收货
                mFooterView.btn_2.text=getString(R.string.confirm_receive)
                mFooterView.btn_1.text=getString(R.string.cancel_order)
                mFooterView.btn_2.visibility=View.VISIBLE
                mFooterView.btn_1.visibility=View.GONE
            }
            "4"->{//已完成
                if (mData.is_commented=="0"){
                    mFooterView.btn_2.text=getString(R.string.tv_btn_comment)
                    mFooterView.btn_2.visibility=View.VISIBLE
                }else{
                    mFooterView.btn_2.visibility=View.GONE
                }

                mFooterView.btn_1.text=getString(R.string.delete_order)
                mFooterView.btn_1.visibility=View.VISIBLE
            }
            "7"->{//退款中
                mFooterView.btn_2.text=getString(R.string.confirm_receive)
                mFooterView.btn_1.text=getString(R.string.delete_order)
                mFooterView.btn_2.visibility=View.GONE
                mFooterView.btn_1.visibility=View.GONE
            }
            "8"->{//已退款
                mFooterView.btn_2.text=getString(R.string.confirm_receive)
                mFooterView.btn_1.text=getString(R.string.delete_order)
                mFooterView.btn_2.visibility=View.GONE
                mFooterView.btn_1.visibility=View.VISIBLE
            }
            "-1"->{//已取消
                mFooterView.btn_2.text=getString(R.string.confirm_receive)
                mFooterView.btn_1.text=getString(R.string.delete_order)
                mFooterView.btn_2.visibility=View.GONE
                mFooterView.btn_1.visibility=View.VISIBLE
            }
        }
        mFooterView.tv_goods_total_price.text="RMB "+mData.goods_total_amount
        mFooterView.tv_freight.text="RMB "+mData.delivery_amount
        mFooterView.tv_order_total_price.text="RMB "+mData.order_amount
        mFooterView.tv_true_pay.text="RMB "+mData.pay_amount
        mFooterView.tv_order_code.text=mData.order_sn
        mFooterView.tv_create_time.text=TimeUtils.millis2String((mData.created_at+"000").toLong(),SimpleDateFormat(" yyyy-MM-dd HH:mm"))
        mFooterView.tv_logistics_order_code.text=mData.express_no
        mFooterView.tv_buzhu.text="RMB "+mData.subsidy_price

    }

    private fun initHeaderView() {
        when(mData.status){
            "1"->{//待付款
                mHeaderView.tv_order_status.text=getString(R.string.wait_pay)
                mHeaderView.iv_order_status.setImageResource(R.mipmap.ic_payment_n)
            }
            "2"->{//待发货
                if (mData.self_pick=="0"){
                    mHeaderView.tv_order_status.text=getString(R.string.wait_send)
                }else{
                    mHeaderView.tv_order_status.text=getString(R.string.pending_collect2)
                }

                mHeaderView.iv_order_status.setImageResource(R.mipmap.ic_thedelivery_n)
            }
            "3"->{//待收货
                mHeaderView.tv_order_status.text=getString(R.string.wait_receive)
                mHeaderView.iv_order_status.setImageResource(R.mipmap.ic_thegoods_n)
            }
            "4"->{//已完成
                mHeaderView.tv_order_status.text=getString(R.string.tv_completed)
                mHeaderView.iv_order_status.setImageResource(R.mipmap.ic_complete_n)
            }
            "7"->{//退款中
                mHeaderView.tv_order_status.text=getString(R.string.tv_refunding)
                mHeaderView.iv_order_status.setImageResource(R.mipmap.ic_arefundof_n)
            }
            "8"->{//已退款
                mHeaderView.tv_order_status.text=getString(R.string.tv_refunded)
                mHeaderView.iv_order_status.setImageResource(R.mipmap.ic_arefund_n)
            }
            "-1"->{//已取消
                mHeaderView.tv_order_status.text=getString(R.string.tv_canceled)
                mHeaderView.iv_order_status.setImageResource(R.mipmap.ic_cancel_n)
            }
        }
        mHeaderView.tv_name.text=mData.address.receiver
        mHeaderView.tv_phone.text=mData.address.mobile
        mHeaderView.tv_address.text=mData.address.delivery_address
    }

    override fun onDataFailure(err: String?,isFirstLoading : Boolean) {
        hideLoadingDialog()
        swipeRefreshLayout.isRefreshing=false
        if (isFirstLoading){
            view_error.visibility=View.VISIBLE
        }
        toast(err!!)
    }

    override fun onConfirmReceiveSuccess() {
        hideLoadingDialog()
        finish()
    }

    override fun onConfirmReceiveFailure(err: String?) {
        hideLoadingDialog()
        toast(err!!)
    }

    override fun onCancelOrderSuccess() {
        hideLoadingDialog()
        finish()
    }

    override fun onCancelOrderFailure(err: String?) {
        hideLoadingDialog()
        toast(err!!)
    }

    override fun onDeleteOrderSuccess() {
        hideLoadingDialog()
        finish()
    }

    override fun onDeleteOrderFailure(err: String?) {
        hideLoadingDialog()
        toast(err!!)
    }

}