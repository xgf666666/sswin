package com.micropole.sxwine.module.home.mvp.model

import com.darkhorse.httphelper.transformer.RxTransformer
import com.micropole.sxwine.bean.ConfirmResult
import com.micropole.sxwine.bean.GoodDetailBean
import com.micropole.sxwine.bean.SettleResult
import com.micropole.sxwine.module.home.mvp.contract.GoodDetailContract
import com.micropole.sxwine.utils.network.API
import com.micropole.sxwine.utils.network.HttpObserver

/**
 * Description:
 * Created by DarkHorse on 2018/5/31.
 */
class GoodDetailModel : GoodDetailContract.Model {
    override fun buyGoods(goods_id: String, quantity: String, httpObserver: HttpObserver<SettleResult>) {
        API.service.buyNow(goods_id,quantity)
                .compose(RxTransformer.io_main())
                .subscribe(httpObserver)
    }

    override fun collectGoods(goods_id: Int, httpObserver: HttpObserver<Any>) {
        API.service.collectGoods(goods_id)
                .compose(RxTransformer.io_main())
                .subscribe(httpObserver)
    }

    override fun cancelGoods(goods_id: Int, httpObserver: HttpObserver<Any>) {
        API.service.cancelGoods(goods_id)
                .compose(RxTransformer.io_main())
                .subscribe(httpObserver)
    }

    override fun addCar(goods_id: Int, quantity: Int, httpObserver: HttpObserver<Any>) {
        API.service.addCar(goods_id, quantity)
                .compose(RxTransformer.io_main())
                .subscribe(httpObserver)
    }


    override fun getData(goods_id: Int, httpObserver: HttpObserver<GoodDetailBean>) {
        API.service.getGoodsDetail(goods_id)
                .compose(RxTransformer.io_main())
                .subscribe(httpObserver)
    }

}
