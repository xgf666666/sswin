package com.micropole.sxwine.module.home.mvp.contract

import com.example.mvpframe.BaseMvpView
import com.micropole.sxwine.bean.ConfirmResult
import com.micropole.sxwine.bean.GoodDetailBean
import com.micropole.sxwine.bean.SettleResult
import com.micropole.sxwine.utils.network.HttpObserver

/**
 * Description:
 * Created by DarkHorse on 2018/5/31.
 */
interface GoodDetailContract {
    interface Model {
        fun getData(goods_id: Int, httpObserver: HttpObserver<GoodDetailBean>)

        fun addCar(goods_id: Int, quantity: Int, httpObserver: HttpObserver<Any>)

        fun collectGoods(goods_id: Int, httpObserver: HttpObserver<Any>)

        fun cancelGoods(goods_id: Int, httpObserver: HttpObserver<Any>)

        fun buyGoods(goods_id: String, quantity: String, httpObserver: HttpObserver<SettleResult>)
    }

    interface View : BaseMvpView {
        fun onSuccess(bean: GoodDetailBean, msg: String)

        fun onFailure(err: String)

        fun addSuccess(msg: String)

        fun addFailure(err: String)

        fun collectSuccess(msg: String)

        fun collectFailure(err: String)

        fun cancelSuccess(msg: String)

        fun cancelFailure(err: String)

        fun buySuccess(result: SettleResult, msg: String)

        fun buyFailure(err: String)
    }

    interface Presenter {
        fun getData(goods_id: Int)

        fun addCar(goods_id: Int, quantity: Int)

        fun collectGoods(goods_id: Int)

        fun cancelGoods(goods_id: Int)

        fun buyGoods(goods_id: String, quantity: String)
    }
}
