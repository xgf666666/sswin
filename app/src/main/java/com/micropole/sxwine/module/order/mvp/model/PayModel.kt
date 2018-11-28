package com.micropole.sxwine.module.order.mvp.model

import com.darkhorse.httphelper.transformer.RxTransformer
import com.micropole.sxwine.bean.PayResult
import com.micropole.sxwine.module.order.mvp.contract.PayContract
import com.micropole.sxwine.module.personal.Bean.CheckPayPwdEntity
import com.micropole.sxwine.utils.network.API
import com.micropole.sxwine.utils.network.HttpObserver

/**
 * Description:
 * Created by DarkHorse on 2018/6/15.
 */
class PayModel : PayContract.Model {
    override fun balancePay(order_id: String, pay_password: String, httpObserver: HttpObserver<Any>) {
        API.service.balancePay(order_id,pay_password)
                .compose(RxTransformer.io_main())
                .subscribe(httpObserver)
    }

    override fun checkPayPwd(httpObserver: HttpObserver<CheckPayPwdEntity>) {
        API.service.checkPayPwd()
                .compose(RxTransformer.io_main())
                .subscribe(httpObserver)
    }

    override fun paypalBack(order_id: String, nonce: String, httpObserver: HttpObserver<Any>) {
        API.service.paypalBack(order_id, nonce)
                .compose(RxTransformer.io_main())
                .subscribe(httpObserver)
    }

    override fun pay(order_id: String, type: String, httpObserver: HttpObserver<PayResult>) {
        API.service.payOrder(order_id, type)
                .compose(RxTransformer.io_main())
                .subscribe(httpObserver)
    }
}
