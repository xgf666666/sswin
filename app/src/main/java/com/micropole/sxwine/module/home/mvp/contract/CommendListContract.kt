package com.micropole.sxwine.module.home.mvp.contract

import com.example.mvpframe.BaseMvpView
import com.micropole.sxwine.bean.ConfirmAddressResult
import com.micropole.sxwine.bean.GoodDetailBean
import com.micropole.sxwine.utils.network.HttpObserver

/**
 * Description:
 * Created by DarkHorse on 2018/9/7.
 */
interface CommendListContract {
    interface Model {
        fun getAllCommend(goods_id: Int, page: Int, httpObserver: HttpObserver<ArrayList<GoodDetailBean.Comment>>)

    }

    interface View : BaseMvpView {
        fun onSuccess(list: ArrayList<GoodDetailBean.Comment>, msg: String)

        fun onFailure(error: String)


    }

    interface Presenter {
        fun getAllCommend(goods_id: Int, page: Int)

    }
}
