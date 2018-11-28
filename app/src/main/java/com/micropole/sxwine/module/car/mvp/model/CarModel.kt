package com.micropole.sxwine.module.car.mvp.model

import com.darkhorse.httphelper.transformer.RxTransformer
import com.micropole.sxwine.bean.CarGoodsBean
import com.micropole.sxwine.bean.CarResult
import com.micropole.sxwine.bean.SettleResult
import com.micropole.sxwine.module.car.mvp.contract.CarContract
import com.micropole.sxwine.utils.network.API
import com.micropole.sxwine.utils.network.HttpObserver

/**
 * Description:
 * Created by DarkHorse on 2018/6/6.
 */
class CarModel : CarContract.Model {
    override fun updateGoods(cart_ids: Int, quantity: Int, httpObserver: HttpObserver<Any>) {
        API.service.updateGoods(cart_ids, quantity)
                .compose(RxTransformer.io_main())
                .subscribe(httpObserver)
    }

    override fun deleteGoods(cart_ids: String, httpObserver: HttpObserver<Any>) {
        API.service.deleteGoods(cart_ids)
                .compose(RxTransformer.io_main())
                .subscribe(httpObserver)
    }

    override fun settleCart(idStr: String, httpObserver: HttpObserver<SettleResult>) {
        API.service.settleCar(idStr)
                .compose(RxTransformer.io_main())
                .subscribe(httpObserver)

    }

    override fun getData(page: Int, httpObserver: HttpObserver<CarResult>) {
        API.service.getCarGoods(page)
                .compose(RxTransformer.io_main())
                .subscribe(httpObserver)
    }

    override fun addGoods(goods_id: Int, httpObserver: HttpObserver<Any>) {
        API.service.addCar(goods_id)
                .compose(RxTransformer.io_main())
                .subscribe(httpObserver)
    }

    override fun subGoods(goods_id: Int, httpObserver: HttpObserver<Any>) {
        API.service.subCar(goods_id)
                .compose(RxTransformer.io_main())
                .subscribe(httpObserver)
    }

}
