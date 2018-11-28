package com.micropole.sxwine.module.personal.mvp.presenter

import com.darkhorse.preferencesmanager.PreferencesHelper
import com.example.mvpframe.BaseMvpPresenter
import com.micropole.sxwine.common.Constants
import com.micropole.sxwine.module.personal.Bean.OrderStatusEntity
import com.micropole.sxwine.module.personal.Bean.UserInfoEntity
import com.micropole.sxwine.module.personal.mvp.contract.MineContract
import com.micropole.sxwine.module.personal.mvp.model.MineModel
import com.micropole.sxwine.utils.network.HttpObserver

/**
 * Description:
 * Created by DarkHorse on 2018/5/28.
 */
class MinePresenter : MineContract.Presenter, BaseMvpPresenter<MineContract.Model, MineContract.View>() {

    override fun getOrderStatus() {
        mModel.getOrderStatus(object : HttpObserver<OrderStatusEntity>(){
            override fun onSuccess(bean: OrderStatusEntity, msg: String) {
                mView?.onGetOrderStatusSuccess(bean)
            }

            override fun onFailure(code: String, msg: String) {
                mView?.onGetOrderStatusFailure(msg)
            }
        })
    }

    override fun getUserInfo() {
        mModel.getUserInfo(object : HttpObserver<UserInfoEntity>(){
            override fun onSuccess(bean: UserInfoEntity, msg: String) {
                PreferencesHelper.put(Constants.USER_INFO,bean)
                mView?.onGetUserInfoSuccess(bean)
            }

            override fun onFailure(code: String, msg: String) {
                mView?.onGetUserInfoFailure(msg)
            }

        })
    }

    override fun createModel(): MineContract.Model = MineModel()
}
