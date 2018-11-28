package com.micropole.sxwine.module.home.mvp.presenter

import com.example.mvpframe.BaseMvpPresenter
import com.micropole.sxwine.bean.HomeResult
import com.micropole.sxwine.bean.BannerBean
import com.micropole.sxwine.bean.ClassifyBean
import com.micropole.sxwine.module.home.mvp.contract.HomeContract
import com.micropole.sxwine.module.home.mvp.model.HomeModel
import com.micropole.sxwine.utils.network.HttpObserver

/**
 * Description:
 * Created by DarkHorse on 2018/6/1.
 */
class HomePresenter : HomeContract.Presenter, BaseMvpPresenter<HomeContract.Model, HomeContract.View>() {
    override fun createModel(): HomeContract.Model = HomeModel()

    override fun getBanner() {
        mModel.getBanner(object : HttpObserver<ArrayList<BannerBean>>() {
            override fun onSuccess(bean: ArrayList<BannerBean>, msg: String) {
                mView?.onBannerSuccess(bean, msg)
            }

            override fun onFailure(code: String, msg: String) {
                mView?.onBannerFailure(msg)
            }
        })
    }

    override fun getClassify() {
        mModel.getClassify(object : HttpObserver<ArrayList<ClassifyBean>>() {
            override fun onSuccess(bean: ArrayList<ClassifyBean>, msg: String) {
                mView?.onClassifySuccess(bean, msg)
            }

            override fun onFailure(code: String, msg: String) {
                mView?.onClassifyFailure(msg)
            }
        })
    }

    override fun getGoods(page: Int) {
        mModel.getGoods(page, object : HttpObserver<HomeResult>() {
            override fun onFailure(code: String, msg: String) {
                mView?.onGoodsFailure(msg)
            }

            override fun onSuccess(bean: HomeResult, msg: String) {
                mView?.onGoodsSuccess(bean, msg)
            }

        })
    }
}
