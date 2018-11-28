package com.micropole.sxwine.module.car.mvp.contract

import com.example.mvpframe.BaseMvpView
import com.micropole.sxwine.bean.CarGoodsBean
import com.micropole.sxwine.bean.CarResult
import com.micropole.sxwine.bean.GoodsBean
import com.micropole.sxwine.bean.SettleResult
import com.micropole.sxwine.utils.network.HttpObserver

/**
 * Description:
 * Created by DarkHorse on 2018/6/6.
 */
interface CarContract {
    interface Model {
        fun getData(page: Int, httpObserver: HttpObserver<CarResult>)

        fun addGoods(goods_id: Int, httpObserver: HttpObserver<Any>)

        fun subGoods(goods_id: Int, httpObserver: HttpObserver<Any>)

        fun settleCart(idStr: String, httpObserver: HttpObserver<SettleResult>)

        fun deleteGoods(cart_ids: String, httpObserver: HttpObserver<Any>)

        fun updateGoods(cart_ids: Int, quantity: Int, httpObserver: HttpObserver<Any>)
    }

    interface View : BaseMvpView {

        fun onSuccess(list: ArrayList<CarGoodsBean>, recommends:ArrayList<GoodsBean>, msg: String)

        fun onFailure(err: String)

        fun addSuccess(msg: String)

        fun addFailure(err: String)

        fun subSuccess(msg: String)

        fun subFailure(err: String)

        fun settleSuccess(result: SettleResult, msg: String)

        fun settleFailure(err: String)

        fun deleteSuccess(msg: String)

        fun deleteFailure(err: String)

        fun updateSuccess(msg: String)

        fun updateFailure(err: String)

    }

    interface Presenter {
        fun getData(page: Int)

        fun addGoods(goods_id: Int)

        fun subGoods(goods_id: Int)

        fun settleCart(idStr: String)

        fun deleteGoods(cart_ids: String)

        fun updateGoods(cart_ids: Int, quantity: Int)
    }
}
