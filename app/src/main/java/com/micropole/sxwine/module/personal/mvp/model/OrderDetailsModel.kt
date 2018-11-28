package com.micropole.sxwine.module.personal.mvp.model

import com.darkhorse.httphelper.transformer.RxTransformer
import com.micropole.sxwine.module.personal.Bean.OrderDetailsEntity
import com.micropole.sxwine.module.personal.mvp.contract.OrderDetailsContract
import com.micropole.sxwine.utils.network.API
import com.micropole.sxwine.utils.network.HttpObserver

/**
 * Created by JohnnyH on 2018/6/19.
 */
class OrderDetailsModel : OrderDetailsContract.Model {

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

    override fun deleteOrder(order_id: String?, httpObserver: HttpObserver<Any>?) {
        API.service.deleteOrder(order_id)
                .compose(RxTransformer.io_main())
                .subscribe(httpObserver!!)
    }

    override fun loadData(order_id: String?, httpObserver: HttpObserver<OrderDetailsEntity>?) {
        API.service.getOrderDetailsData(order_id)
                .compose(RxTransformer.io_main())
                .subscribe(httpObserver!!)
    }
}