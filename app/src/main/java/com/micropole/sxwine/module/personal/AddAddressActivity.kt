package com.micropole.sxwine.module.personal

import android.view.View
import com.example.mvpframe.BaseMvpActivity
import com.micropole.sxwine.R
import com.micropole.sxwine.base.hideLoadingDialog
import com.micropole.sxwine.base.initToolBar
import com.micropole.sxwine.base.showLoadingDialog
import com.micropole.sxwine.base.toast
import com.micropole.sxwine.module.personal.Bean.AddressManagerEntity
import com.micropole.sxwine.module.personal.mvp.contract.AddAddressContract
import com.micropole.sxwine.module.personal.mvp.presenter.AddAddressPresenter
import kotlinx.android.synthetic.main.activity_add_address.*
import kotlinx.android.synthetic.main.item_toolbar.*

/**
 * Created by JohnnyH on 2018/6/8.
 */
class AddAddressActivity : BaseMvpActivity<AddAddressContract.Model,AddAddressContract.View, AddAddressPresenter>(),AddAddressContract.View, View.OnClickListener {

    private lateinit var mType : String //1: 直接添加 2:编辑
    private lateinit var mAddress_id : String

    override fun createPresenter(): AddAddressPresenter = AddAddressPresenter()

    override fun getLayoutId(): Int = R.layout.activity_add_address

    override fun initView() {
        mType=intent.getStringExtra("type")
        initToolBar(intent.getStringExtra("title"))
        toolbar.setNavigationOnClickListener {
            finish()
        }
        btn_save.setOnClickListener(this)
        iv_no_off.setOnClickListener(this)
        if ("2"==mType){//1: 直接添加 2:编辑
            val addressManagerEntity = intent.getSerializableExtra("AddressManagerEntity") as AddressManagerEntity
            mAddress_id=addressManagerEntity.address_id
            ed_receiver.setText(addressManagerEntity.receiver)
            ed_phone.setText(addressManagerEntity.mobile)
            ed_address.setText(addressManagerEntity.address_detail)
            is_default=addressManagerEntity.is_default
            changeUI(is_default)
        }
    }

    override fun initData() {

    }

    override fun initListener() {

    }

    private var is_default : String = "0"//0:不是默认地址 1:是默认地址
    override fun onClick(view: View?) {
        when(view!!.id){
            R.id.iv_no_off->{//是否设为默认地址
                if ("0"==is_default){
                    is_default="1"
                }else{
                    is_default="0"
                }
                changeUI(is_default)

            }
            R.id.btn_save->{//保存
                val receiver = ed_receiver.text.toString().trim()
                val phone = ed_phone.text.toString().trim()
                val address_details = ed_address.text.toString().trim()
                showLoadingDialog()
                if ("2"==mType){//1: 直接添加 2:编辑
                    mPresenter.compileAddress(phone,receiver,address_details,is_default,mAddress_id)
                }else{
                    mPresenter.addAddress(phone,receiver,address_details,is_default)
                }

            }
        }
    }

    private fun changeUI(is_default : String){
        if ("0"==is_default){//不是默认地址
            iv_no_off.setImageResource(R.mipmap.home_hui_yuanss)
        }else{
            iv_no_off.setImageResource(R.mipmap.home_bai_yuanss)
        }
    }

    override fun onAddAddressSuccess() {
        hideLoadingDialog()
        finish()
    }

    override fun onAddAddressFailure(err: String?) {
        hideLoadingDialog()
        toast(err!!)
    }

    override fun onCompileAddressSuccess() {
        hideLoadingDialog()
        finish()
    }

    override fun onCompileAddressFailure(err: String?) {
        hideLoadingDialog()
        toast(err!!)
    }
}