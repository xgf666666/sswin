package com.micropole.sxwine.module.home.mvp.presenter

import com.example.mvpframe.BaseMvpPresenter
import com.micropole.sxwine.base.toast
import com.micropole.sxwine.bean.ConfirmAddressResult
import com.micropole.sxwine.bean.GoodDetailBean
import com.micropole.sxwine.module.home.mvp.contract.CommendListContract
import com.micropole.sxwine.module.home.mvp.model.CommendListModel
import com.micropole.sxwine.utils.network.HttpObserver

/**
 * Description:
 * Created by DarkHorse on 2018/9/7.
 */
class CommendListPresenter : CommendListContract.Presenter, BaseMvpPresenter<CommendListContract.Model, CommendListContract.View>() {


    override fun createModel(): CommendListContract.Model = CommendListModel()

    override fun getAllCommend(goods_id: Int, page: Int) {
        mModel.getAllCommend(goods_id, page, object : HttpObserver<ArrayList<GoodDetailBean.Comment>>() {
            override fun onSuccess(bean: ArrayList<GoodDetailBean.Comment>, msg: String) {
                mView?.onSuccess(bean, msg)
            }

            override fun onFailure(code: String, msg: String) {
                mView?.onFailure(msg)
            }
        })
    }

}
