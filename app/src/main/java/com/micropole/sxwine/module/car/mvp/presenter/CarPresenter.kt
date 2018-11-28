package com.micropole.sxwine.module.car.mvp.presenter

import com.example.mvpframe.BaseMvpPresenter
import com.micropole.sxwine.bean.CarGoodsBean
import com.micropole.sxwine.bean.CarResult
import com.micropole.sxwine.bean.GoodsBean
import com.micropole.sxwine.bean.SettleResult
import com.micropole.sxwine.module.car.mvp.contract.CarContract
import com.micropole.sxwine.module.car.mvp.model.CarModel
import com.micropole.sxwine.utils.network.HttpObserver

/**
 * Description:
 * Created by DarkHorse on 2018/6/6.
 */
class CarPresenter : CarContract.Presenter, BaseMvpPresenter<CarContract.Model, CarContract.View>() {
    override fun updateGoods(cart_ids: Int, quantity: Int) {
        mModel.updateGoods(cart_ids, quantity, object : HttpObserver<Any>() {
            override fun onSuccess(bean: Any, msg: String) {
                mView?.updateSuccess(msg)
            }

            override fun onFailure(code: String, msg: String) {
                mView?.updateFailure(msg)
            }
        })
    }

    override fun deleteGoods(cart_ids: String) {
        mModel.deleteGoods(cart_ids, object : HttpObserver<Any>() {
            override fun onSuccess(bean: Any, msg: String) {
                mView?.deleteSuccess(msg)
            }

            override fun onFailure(code: String, msg: String) {
                mView?.deleteFailure(msg)
            }
        })
    }

    override fun settleCart(idStr: String) {
        mModel.settleCart(idStr, object : HttpObserver<SettleResult>() {
            override fun onSuccess(bean: SettleResult, msg: String) {
                mView?.settleSuccess(bean, msg)
            }

            override fun onFailure(code: String, msg: String) {
                mView?.settleFailure(msg)
            }

        })
    }

    override fun addGoods(goods_id: Int) {
        mModel.addGoods(goods_id, object : HttpObserver<Any>() {
            override fun onSuccess(bean: Any, msg: String) {
                mView?.addSuccess(msg)
            }

            override fun onFailure(code: String, msg: String) {
                mView?.addFailure(msg)
            }
        })
    }

    override fun subGoods(goods_id: Int) {
        mModel.subGoods(goods_id, object : HttpObserver<Any>() {
            override fun onSuccess(bean: Any, msg: String) {
                mView?.subSuccess(msg)
            }

            override fun onFailure(code: String, msg: String) {
                mView?.subFailure(msg)
            }
        })
    }

    override fun createModel(): CarContract.Model = CarModel()

    override fun getData(page: Int) {
        mModel.getData(page, object : HttpObserver<CarResult>() {
            override fun onSuccess(bean: CarResult, msg: String) {
                val goods = ArrayList<CarGoodsBean>()
                val recommends = ArrayList<GoodsBean>()
                for (c in bean.cart) {
                    val good = c.goods
                    goods.add(CarGoodsBean(c.cartId, c.goodsId, c.quantity,
                            GoodsBean(good.goodsId, good.goodsName, good.coverImg, ArrayList(), good.mallPrice, good.shopPrice, good.introduce, good.enDecp, good.soldCount, "", "", "", "", "", "", good.thumb_img),
                            false))
                }
                for (r in bean.recommendList) {
                    recommends.add(GoodsBean(r.goodsId, r.goodsName, r.coverImg, ArrayList(), r.mallPrice, r.shopPrice, "", "", r.soldCount, "", "", "", "", "", "", ""))
                }
                mView?.onSuccess(goods, recommends, msg)
            }

            override fun onFailure(code: String, msg: String) {
                mView?.onFailure(msg)
            }
        })
    }
}
