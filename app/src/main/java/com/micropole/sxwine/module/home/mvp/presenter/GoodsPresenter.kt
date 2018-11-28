package com.micropole.sxwine.module.home.mvp.presenter

import com.example.mvpframe.BaseMvpPresenter
import com.micropole.sxwine.bean.HomeResult
import com.micropole.sxwine.module.home.mvp.contract.GoodsContract
import com.micropole.sxwine.module.home.mvp.model.GoodsModel
import com.micropole.sxwine.utils.network.HttpObserver

/**
 * Description:
 * Created by DarkHorse on 2018/6/4.
 */
class GoodsPresenter : GoodsContract.Presenter, BaseMvpPresenter<GoodsContract.Model, GoodsContract.View>() {
    override fun getGoods(category_id: String, page: Int, sort: String, direction: String) {
        mModel.getGoods(category_id, page, sort, direction, object : HttpObserver<HomeResult>() {

            override fun onSuccess(bean: HomeResult, msg: String) {
                mView?.onSuccess(bean.goods, msg)
            }

            override fun onFailure(code: String, msg: String) {
                mView?.onFailure(msg)
            }
        })
    }

    override fun createModel(): GoodsContract.Model = GoodsModel()
}
