package com.micropole.sxwine.module.order.mvp.presenter

import com.example.mvpframe.BaseMvpPresenter
import com.micropole.sxwine.base.toast
import com.micropole.sxwine.bean.PayResult
import com.micropole.sxwine.module.order.mvp.contract.PayContract
import com.micropole.sxwine.module.order.mvp.model.PayModel
import com.micropole.sxwine.module.personal.Bean.CheckPayPwdEntity
import com.micropole.sxwine.utils.network.HttpObserver

/**
 * Description:
 * Created by DarkHorse on 2018/6/15.
 */
class PayPresenter : PayContract.Presenter, BaseMvpPresenter<PayContract.Model, PayContract.View>() {
    override fun balancePay(order_id: String, pay_password: String) {
        mModel.balancePay(order_id, pay_password, object : HttpObserver<Any>() {
            override fun onSuccess(bean: Any, msg: String) {
                mView?.balanceSuccess(msg)
            }

            override fun onFailure(code: String, msg: String) {
                mView?.balanceFailure(msg)
            }
        })
    }

    override fun createModel(): PayContract.Model = PayModel()

    override fun pay(order_id: String, type: String) {
        mModel.pay(order_id, type, object : HttpObserver<PayResult>() {
            override fun onSuccess(bean: PayResult, msg: String) {
                mView?.onSuccess(bean)
            }

            override fun onFailure(code: String, msg: String) {
                mView?.onFailure(msg)
            }
        })
    }

    override fun paypalBack(order_id: String, nonce: String) {
        mModel.paypalBack(order_id, nonce, object : HttpObserver<Any>() {
            override fun onSuccess(bean: Any, msg: String) {
                mView?.paypalSuccess(msg)
            }

            override fun onFailure(code: String, msg: String) {
                mView?.paypalFailure(msg)
            }
        })
    }

    override fun checkPayPwd() {
        mModel.checkPayPwd(object : HttpObserver<CheckPayPwdEntity>() {
            override fun onSuccess(bean: CheckPayPwdEntity, msg: String) {
                mView?.onCheckPayPwdSuccess(bean)
            }

            override fun onFailure(code: String, msg: String) {
                mView?.onCheckPayPwdFailure(msg)
            }

        })
    }
}
