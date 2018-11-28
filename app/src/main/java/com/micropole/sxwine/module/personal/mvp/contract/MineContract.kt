package com.micropole.sxwine.module.personal.mvp.contract

import com.example.mvpframe.BaseMvpView
import com.micropole.sxwine.module.personal.Bean.OrderStatusEntity
import com.micropole.sxwine.module.personal.Bean.UserInfoEntity
import com.micropole.sxwine.utils.network.HttpObserver


/**
 * Description:
 * Created by DarkHorse on 2018/5/28.
 */
interface MineContract {

    interface View : BaseMvpView {

        fun onGetUserInfoSuccess(data: UserInfoEntity)

        fun onGetUserInfoFailure(err : String)

        fun onGetOrderStatusSuccess(data : OrderStatusEntity)
        fun onGetOrderStatusFailure(err : String)
    }

    interface Presenter{

        fun getUserInfo()

        fun getOrderStatus()
    }

    interface Model{

        fun getUserInfo(httpObserver: HttpObserver<UserInfoEntity>)

        fun getOrderStatus(httpObserver: HttpObserver<OrderStatusEntity>)
    }
}
