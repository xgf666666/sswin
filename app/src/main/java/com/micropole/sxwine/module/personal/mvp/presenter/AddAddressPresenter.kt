package com.micropole.sxwine.module.personal.mvp.presenter

import android.text.TextUtils
import com.blankj.utilcode.util.Utils
import com.example.mvpframe.BaseMvpPresenter
import com.micropole.sxwine.R
import com.micropole.sxwine.base.toast
import com.micropole.sxwine.module.personal.Bean.AddAddressEntity
import com.micropole.sxwine.module.personal.mvp.contract.AddAddressContract
import com.micropole.sxwine.module.personal.mvp.model.AddAddressModel
import com.micropole.sxwine.utils.network.HttpObserver

/**
 * Created by JohnnyH on 2018/6/11.
 */
class AddAddressPresenter : BaseMvpPresenter<AddAddressContract.Model,AddAddressContract.View>(),AddAddressContract.Presenter {

    override fun compileAddress(mobile: String?, receiver: String?, address_detail: String?, is_default: String?, address_id: String?) {
        if (TextUtils.isEmpty(receiver)){
            mView?.onCompileAddressFailure(Utils.getApp().getString(R.string.input_receive_name))
            return
        }else if (TextUtils.isEmpty(mobile)){
            mView?.onCompileAddressFailure(Utils.getApp().getString(R.string.input_phone))
            return
        }else if (mobile!!.length!=11){
            mView?.onCompileAddressFailure(Utils.getApp().getString(R.string.input_phone))
            return
        }else if (TextUtils.isEmpty(address_detail)){
            mView?.onCompileAddressFailure(Utils.getApp().getString(R.string.input_receive_address))
            return
        }

        mModel.compileAddress(mobile,receiver,address_detail,is_default,address_id,object : HttpObserver<AddAddressEntity>(){
            override fun onSuccess(bean : AddAddressEntity, msg : String) {
                toast(msg)
                mView?.onCompileAddressSuccess()
            }

            override fun onFailure(code: String, msg: String) {
                mView?.onCompileAddressFailure(msg)
            }

        })
    }

    override fun addAddress(mobile: String?, receiver: String?, address_detail: String?, is_default: String?) {
        if (TextUtils.isEmpty(receiver)){
            mView?.onAddAddressFailure("请输入收货人姓名")
            return
        }else if (TextUtils.isEmpty(mobile)){
            mView?.onAddAddressFailure("请输入收货人手机号")
            return
        }else if (mobile!!.length!=11){
            mView?.onAddAddressFailure("请输入正确的手机号")
            return
        }else if (TextUtils.isEmpty(address_detail)){
            mView?.onAddAddressFailure("请输入收货地址")
            return
        }

        mModel.addAddress(mobile,receiver,address_detail,is_default,object : HttpObserver<AddAddressEntity>(){
            override fun onSuccess(bean: AddAddressEntity, msg: String) {
                toast(msg)
                mView?.onAddAddressSuccess()
            }

            override fun onFailure(code: String, msg: String) {
                mView?.onAddAddressFailure(msg)
            }

        })
    }

    override fun createModel(): AddAddressContract.Model = AddAddressModel()
}