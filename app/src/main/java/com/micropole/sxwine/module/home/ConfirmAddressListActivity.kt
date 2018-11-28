package com.micropole.sxwine.module.home

import android.content.Intent
import android.support.v7.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.mvpframe.BaseMvpActivity
import com.micropole.sxwine.R
import com.micropole.sxwine.R.id.iv_back_2
import com.micropole.sxwine.adapter.ConfirmAddressAdapter
import com.micropole.sxwine.base.toast
import com.micropole.sxwine.bean.ConfirmAddressResult
import com.micropole.sxwine.bean.GoodDetailBean
import com.micropole.sxwine.bean.RcvConfirmAddressBean
import com.micropole.sxwine.module.home.mvp.contract.ConfirmAddressContract
import com.micropole.sxwine.module.home.mvp.presenter.ConfirmAddressPresenter
import kotlinx.android.synthetic.main.activity_confirm.*
import kotlinx.android.synthetic.main.activity_confirm_address.*
import kotlinx.android.synthetic.main.item_rcv_goods.*

/**
 * Description:
 * Created by DarkHorse on 2018/9/14.
 */
class ConfirmAddressListActivity : BaseMvpActivity<ConfirmAddressContract.Model, ConfirmAddressContract.View, ConfirmAddressPresenter>(), ConfirmAddressContract.View {
    override fun createPresenter(): ConfirmAddressPresenter = ConfirmAddressPresenter()

    override fun getLayoutId(): Int = R.layout.activity_confirm_address

    private var mPos = 0

    private lateinit var rcv_address: RecyclerView
    private val mAddressList = ArrayList<RcvConfirmAddressBean>()
    private val mAddressAdapter by lazy {
        ConfirmAddressAdapter(R.layout.rcv_confirm_address_coice)
    }

    override fun initView() {
        mAddressAdapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
            if (mPos != position) {
                mAddressList[mPos].isCheck = false
                mAddressAdapter.notifyItemChanged(mPos)
                mPos = position
                mAddressList[mPos].isCheck = true
                mAddressAdapter.notifyItemChanged(mPos)
            }
        }
        rcv_address = findViewById(R.id.rcv_address)
        rcv_address.adapter = mAddressAdapter
    }

    override fun initListener() {
        iv_back_2.setOnClickListener {
            onBackPressed()
        }

        tv_submit.setOnClickListener {
            val name = et_name.text.toString()
            if(name == ""){
                toast( resources.getString(R.string.input_receive_name))
                return@setOnClickListener
            }
            val phone = et_phone.text.toString()
            if(phone == ""){
                toast( resources.getString(R.string.input_phone))
                return@setOnClickListener
            }
            intent.putExtra("addressId", mAddressAdapter.data[mPos].id)
            intent.putExtra("name", name)
            intent.putExtra("phone", phone)
            setResult(321, intent)
            finish()
        }
    }

    override fun initData() {
        mPresenter.getAddress()
    }


    override fun getAddressSuccess(result: ConfirmAddressResult, msg: String) {
        mAddressList.clear()
        for (b in result.list) {
            mAddressList.add(RcvConfirmAddressBean(b.addressId, b.shopName, b.address, false))
        }
        if (mAddressList.size > 0) {
            mPos = 0
            mAddressList[mPos].isCheck = true
            mAddressAdapter.setNewData(mAddressList)
        } else {
            mAddressAdapter.setEmptyView(R.layout.layout_empty_view)
        }

        et_name.setText(result.defaultInfo.receiver)
        et_phone.setText(result.defaultInfo.mobile)
    }

    override fun getAdderssFailure(error: String) {
        toast(error)
    }
}
