package com.micropole.sxwine.module.home.mvp.presenter

import com.example.mvpframe.BaseMvpPresenter
import com.micropole.sxwine.bean.GoodDetailBean
import com.micropole.sxwine.bean.SettleResult
import com.micropole.sxwine.module.home.mvp.contract.GoodDetailContract
import com.micropole.sxwine.module.home.mvp.model.GoodDetailModel
import com.micropole.sxwine.utils.network.HttpObserver

/**
 * Description:
 * Created by DarkHorse on 2018/5/31.
 */
class GoodDetailPresenter : GoodDetailContract.Presenter, BaseMvpPresenter<GoodDetailContract.Model, GoodDetailContract.View>() {
    override fun buyGoods(goods_id: String, quantity: String) {
        mModel.buyGoods(goods_id, quantity, object : HttpObserver<SettleResult>() {
            override fun onSuccess(bean: SettleResult, msg: String) {
                mView?.buySuccess(bean, msg)
            }

            override fun onFailure(code: String, msg: String) {
                mView?.buyFailure(msg)
            }
        })
    }

    override fun collectGoods(goods_id: Int) {
        mModel.collectGoods(goods_id, object : HttpObserver<Any>() {
            override fun onSuccess(bean: Any, msg: String) {
                mView?.collectSuccess(msg)
            }

            override fun onFailure(code: String, msg: String) {
                mView?.collectFailure(msg)
            }
        })
    }

    override fun cancelGoods(goods_id: Int) {
        mModel.cancelGoods(goods_id, object : HttpObserver<Any>() {
            override fun onSuccess(bean: Any, msg: String) {
                mView?.cancelSuccess(msg)
            }

            override fun onFailure(code: String, msg: String) {
                mView?.cancelFailure(msg)
            }
        })
    }

    override fun addCar(goods_id: Int, quantity: Int) {
        mModel.addCar(goods_id, quantity, object : HttpObserver<Any>() {
            override fun onSuccess(bean: Any, msg: String) {
                mView?.addSuccess(msg)
            }

            override fun onFailure(code: String, msg: String) {
                mView?.addFailure(msg)
            }
        })
    }

    override fun getData(goods_id: Int) {
        mModel.getData(goods_id, object : HttpObserver<GoodDetailBean>() {
            override fun onSuccess(bean: GoodDetailBean, msg: String) {
                mView?.onSuccess(bean, msg)
            }

            override fun onFailure(code: String, msg: String) {
                mView?.onFailure(msg)
            }

        })
    }

    override fun createModel(): GoodDetailContract.Model = GoodDetailModel()

}
