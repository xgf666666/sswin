package com.micropole.sxwine.module.home.mvp.contract

import com.example.mvpframe.BaseMvpView
import com.micropole.sxwine.bean.GoodsBean
import com.micropole.sxwine.bean.HomeResult
import com.micropole.sxwine.utils.network.HttpObserver

/**
 * Description:
 * Created by DarkHorse on 2018/6/4.
 */
interface GoodsContract {
    interface Model {
        fun getGoods(category_id: String, page: Int, sort: String, direction: String, httpObserver: HttpObserver<HomeResult>)
    }

    interface View : BaseMvpView {
        fun onSuccess(list: ArrayList<GoodsBean>, msg: String)

        fun onFailure(err: String)
    }

    interface Presenter {
        fun getGoods(category_id: String, page: Int, sort: String, direction: String)
    }
}
