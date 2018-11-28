package com.micropole.sxwine.module.order.mvp.contract

import com.example.mvpframe.BaseMvpView
import com.micropole.sxwine.bean.PayResult
import com.micropole.sxwine.module.personal.Bean.CheckPayPwdEntity
import com.micropole.sxwine.utils.network.HttpObserver

/**
 * Description:
 * Created by DarkHorse on 2018/6/15.
 */
interface PayContract {
    interface Model {
        fun pay(order_id: String, type: String, httpObserver: HttpObserver<PayResult>)

        fun paypalBack(order_id: String, nonce: String, httpObserver: HttpObserver<Any>)

        fun checkPayPwd(httpObserver: HttpObserver<CheckPayPwdEntity>)

        fun balancePay(order_id: String,pay_password: String,httpObserver: HttpObserver<Any>)
    }

    interface View : BaseMvpView {
        fun onSuccess(result: PayResult)
        fun onFailure(error: String)

        fun paypalSuccess(msg: String)
        fun paypalFailure(eroor: String)

        fun onCheckPayPwdSuccess(data: CheckPayPwdEntity)
        fun onCheckPayPwdFailure(err: String)

        fun balanceSuccess(msg:String)
        fun balanceFailure(err: String)
    }

    interface Presenter {
        fun pay(order_id: String, type: String)

        fun paypalBack(order_id: String, nonce: String)

        fun checkPayPwd()

        fun balancePay(order_id: String, pay_password: String)
    }
}
