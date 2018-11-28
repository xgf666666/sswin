package com.micropole.sxwine.module.personal.mvp.model

import com.darkhorse.httphelper.transformer.RxTransformer
import com.micropole.sxwine.module.personal.Bean.MyOrderItemEntity
import com.micropole.sxwine.module.personal.mvp.contract.MyOrderContract
import com.micropole.sxwine.utils.network.API
import com.micropole.sxwine.utils.network.HttpObserver

/**
 * Created by JohnnyH on 2018/6/15.
 */
class MyOrderModel : MyOrderContract.Model {

    override fun deleteOrder(order_id: String?, httpObserver: HttpObserver<Any>?) {
        API.service.deleteOrder(order_id)
                .compose(RxTransformer.io_main())
                .subscribe(httpObserver!!)
    }

    override fun confirmReceive(order_id: String?, httpObserver: HttpObserver<Any>?) {
        API.service.confirmReceive(order_id)
                .compose(RxTransformer.io_main())
                .subscribe(httpObserver!!)
    }

    override fun cancelOrder(order_id: String?, httpObserver: HttpObserver<Any>?) {
        API.service.cancelOrder(order_id)
                .compose(RxTransformer.io_main())
                .subscribe(httpObserver!!)
    }

    override fun loadData(page: String?, per_page: String?, order_status: String?, httpObserver: HttpObserver<List<MyOrderItemEntity>>?) {
        API.service.getMyOrderData(page,per_page,order_status)
                .compose(RxTransformer.io_main())
                .subscribe(httpObserver!!)
    }
}