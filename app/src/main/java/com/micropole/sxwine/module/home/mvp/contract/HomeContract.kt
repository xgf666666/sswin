package com.micropole.sxwine.module.home.mvp.contract

import com.example.mvpframe.BaseMvpView
import com.micropole.sxwine.bean.BannerBean
import com.micropole.sxwine.bean.ClassifyBean
import com.micropole.sxwine.bean.HomeResult
import com.micropole.sxwine.utils.network.HttpObserver

/**
 * Description:
 * Created by DarkHorse on 2018/6/1.
 */
interface HomeContract {
    interface Model {
        fun getBanner(httpObserver: HttpObserver<ArrayList<BannerBean>>)

        fun getClassify( httpObserver: HttpObserver<ArrayList<ClassifyBean>>)

        fun getGoods(page: Int, httpObserver: HttpObserver<HomeResult>)

    }

    interface View : BaseMvpView {
        fun onBannerSuccess(list: ArrayList<BannerBean>, msg: String)

        fun onBannerFailure(err: String)

        fun onClassifySuccess(list: ArrayList<ClassifyBean>, msg: String)

        fun onClassifyFailure(err: String)

        fun onGoodsSuccess(list: HomeResult, msg: String)

        fun onGoodsFailure(err: String)
    }


    interface Presenter {
        fun getBanner()

        fun getClassify()

        fun getGoods(page: Int)
    }
}
