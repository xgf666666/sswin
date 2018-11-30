package com.micropole.sxwine.module.order

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.mvpframe.BaseMvpActivity
import com.micropole.sxwine.R
import com.micropole.sxwine.adapter.ConfirmGoodsAdapter
import com.micropole.sxwine.base.hideLoadingDialog
import com.micropole.sxwine.base.showLoadingDialog
import com.micropole.sxwine.base.toast
import com.micropole.sxwine.bean.CarGoodsBean2
import com.micropole.sxwine.bean.ConfirmResult
import com.micropole.sxwine.bean.SettleResult
import com.micropole.sxwine.module.home.ConfirmAddressListActivity
import com.micropole.sxwine.module.order.mvp.contract.ConfirmContract
import com.micropole.sxwine.module.order.mvp.presenter.ConfirmPresenter
import com.micropole.sxwine.module.personal.AddressManagerActivity
import com.micropole.sxwine.module.personal.Bean.AddressManagerEntity
import kotlinx.android.synthetic.main.activity_confirm.*

class ConfirmActivity : BaseMvpActivity<ConfirmContract.Model, ConfirmContract.View, ConfirmPresenter>(), View.OnClickListener, ConfirmContract.View {

    private var mResult: SettleResult? = null

    private lateinit var mCarGoodsAdapter: ConfirmGoodsAdapter
    private lateinit var mCarGoodsList: ArrayList<CarGoodsBean2>

    private lateinit var mAddressId: String
    private lateinit var mTempId: String
    private lateinit var tv_type_1: TextView
    private lateinit var tv_type_2: TextView
    private lateinit var iv_back: ImageView

    private lateinit var ll_detai_l: RelativeLayout
    private lateinit var ll_detai_2: RelativeLayout

    private var mType = true

    override fun createPresenter(): ConfirmPresenter = ConfirmPresenter()

    override fun getLayoutId(): Int = R.layout.activity_confirm

    override fun initView() {
        tv_type_1 = findViewById(R.id.tv_type_1)
        tv_type_2 = findViewById(R.id.tv_type_2)
        ll_detai_l = findViewById(R.id.detail)
        ll_detai_2 = findViewById(R.id.detail_2)
        iv_back = findViewById(R.id.iv_back)

        initRecyclerView()
    }

    private fun initRecyclerView() {
        mCarGoodsList = ArrayList()
        mCarGoodsAdapter = ConfirmGoodsAdapter(R.layout.item_rcv_order_goods, mCarGoodsList)
        rcv_car_goods.adapter = mCarGoodsAdapter
        rcv_car_goods.isNestedScrollingEnabled=false
    }

    override fun initListener() {
        submit_order.setOnClickListener(this)
        detail.setOnClickListener(this)
        detail_2.setOnClickListener(this)

        tv_type_1.setOnClickListener {
            tv_type_1.isSelected = true
            tv_type_2.isSelected = false
            ll_detai_l.visibility = View.VISIBLE
            ll_detai_2.visibility = View.GONE
            mType = true
        }

        tv_type_2.setOnClickListener {
            tv_type_2.isSelected = true
            tv_type_1.isSelected = false
            ll_detai_l.visibility = View.GONE
            ll_detai_2.visibility = View.VISIBLE
            mType = false
        }

        iv_back.setOnClickListener {
            onBackPressed()
        }
    }

    override fun initData() {
        tv_type_1.performClick()

        if (mResult == null) {
            mResult = getBundle()?.getSerializable("result") as SettleResult
        }
        mCarGoodsList.clear()
        mCarGoodsList.addAll(mResult!!.cart)
        mCarGoodsAdapter.setNewData(mCarGoodsList)
        tv_carriage.text = "RMB " + mResult!!.expressFee
        tv_money.text = "RMB " + mResult!!.orderAmount
        tv_allPay.text = "RMB " + mResult!!.goodsTotalAmount
        tv_carriage_buzhu.text= "RMB " + mResult!!.subsidy_price

        val address = mResult!!.address
        if (address.receiver != "") {
            tv_name.text = address.receiver
            tv_phone.text = address.mobile
            tv_address.text = address.address_detail

            tv_hint.visibility = View.GONE
            tv_name.visibility = View.VISIBLE
            tv_phone.visibility = View.VISIBLE
            tv_address.visibility = View.VISIBLE
        } else {
            tv_hint.visibility = View.VISIBLE
            tv_name.visibility = View.GONE
            tv_phone.visibility = View.GONE
            tv_address.visibility = View.GONE
        }

        mAddressId = mResult!!.address.address_id
        mTempId = mResult!!.tempId
    }

    override fun onClick(v: View?) {
        when (v) {
            submit_order -> {
                if ((tv_hint.visibility == View.GONE && mType) || (tv_hint_2.visibility == View.GONE && !mType)) {
                    showLoadingDialog()
                    mPresenter.submitOrder(mTempId)
                    return
                }else if(tv_hint_2.visibility == View.VISIBLE && !mType){
                    toast(getString(R.string.collection_address))
                    return
                } else {
                    toast(getString(R.string.selector_address))
                }
            }
            detail -> {
                val bundle = Bundle()
                bundle.putBoolean("isOrder", true)
                val intent = Intent(this@ConfirmActivity, AddressManagerActivity::class.java)
                intent.putExtra("data", bundle)
                this@ConfirmActivity.startActivityForResult(intent, 100)
            }
            detail_2 -> {
                val intent = Intent(this@ConfirmActivity, ConfirmAddressListActivity::class.java)
                this@ConfirmActivity.startActivityForResult(intent, 200)
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            100 -> {
                if (data != null) {
                    val address = data.getSerializableExtra("address") as AddressManagerEntity
                    mAddressId = address.address_id
                    showLoadingDialog()
                    mPresenter.updateOrder(mTempId, mAddressId, 0)
                }
            }
            200 -> {
                if (data != null) {
                    val addressId = data.getStringExtra("addressId")
                    val name = data.getStringExtra("name")
                    val phone = data.getStringExtra("phone")
                    mAddressId = addressId
                    showLoadingDialog()
                    mPresenter.updateOrder(mTempId, null, 1, mAddressId.toInt(), name, phone)
                }
            }
        }
    }

    override fun onSuccess(bean: ConfirmResult, msg: String) {
        hideLoadingDialog()
        val bundle = Bundle()
        bundle.putString("order_id", bean.order_id)
        bundle.putString("pay_amount", bean.pay_amount)
        startActivity(PayActivity::class.java, bundle, true)
        finish()
    }

    override fun onFailure(err: String) {
        toast(err)
        hideLoadingDialog()
    }

    override fun onUpdateSuccess(bean: SettleResult, msg: String) {
        mResult = bean
        if (mType) {
            if (mResult == null) {
                mResult = getBundle()?.getSerializable("result") as SettleResult
            }
            mCarGoodsList.clear()
            mCarGoodsList.addAll(mResult!!.cart)
            mCarGoodsAdapter.setNewData(mCarGoodsList)
            tv_carriage.text = "RMB" + mResult!!.expressFee
            tv_money.text = "RMB" + mResult!!.orderAmount
            tv_allPay.text = "RMB" + mResult!!.goodsTotalAmount

            val address = mResult!!.address
            if (address.receiver != "") {
                tv_name.text = address.receiver
                tv_phone.text = address.mobile
                tv_address.text = address.address_detail

                tv_hint.visibility = View.GONE
                tv_name.visibility = View.VISIBLE
                tv_phone.visibility = View.VISIBLE
                tv_address.visibility = View.VISIBLE
            } else {
                tv_hint.visibility = View.VISIBLE
                tv_name.visibility = View.GONE
                tv_phone.visibility = View.GONE
                tv_address.visibility = View.GONE
            }

            mAddressId = mResult!!.address.address_id
            mTempId = mResult!!.tempId
        } else {
            if (mResult == null) {
                mResult = getBundle()?.getSerializable("result") as SettleResult
            }
            mCarGoodsList.clear()
            mCarGoodsList.addAll(mResult!!.cart)
            mCarGoodsAdapter.setNewData(mCarGoodsList)
            tv_carriage.text = "RMB" + mResult!!.expressFee
            tv_money.text = "RMB" + mResult!!.orderAmount
            tv_allPay.text = "RMB" + mResult!!.goodsTotalAmount

            val address = mResult!!.address
            if (address.receiver != "") {
                tv_name_2.text = address.receiver
                tv_phone_2.text = address.mobile
                tv_address_2.text = address.address_detail

                tv_hint_2.visibility = View.GONE
                tv_name_2.visibility = View.VISIBLE
                tv_phone_2.visibility = View.VISIBLE
                tv_address_2.visibility = View.VISIBLE
            } else {
                tv_hint_2.visibility = View.VISIBLE
                tv_name_2.visibility = View.GONE
                tv_phone_2.visibility = View.GONE
                tv_address_2.visibility = View.GONE
            }

            mAddressId = mResult!!.address.address_id
            mTempId = mResult!!.tempId
        }
        hideLoadingDialog()
    }

    override fun onUpdateFailure(err: String) {
        hideLoadingDialog()
        finish()
    }

}
