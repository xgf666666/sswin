package com.micropole.sxwine.module.home.mvp.presenter

import com.example.mvpframe.BaseMvpPresenter
import com.micropole.sxwine.base.toast
import com.micropole.sxwine.bean.HomeResult
import com.micropole.sxwine.module.home.mvp.contract.SearchContract
import com.micropole.sxwine.module.home.mvp.model.SearchModel
import com.micropole.sxwine.utils.network.HttpObserver

/**
 * Description:
 * Created by DarkHorse on 2018/6/8.
 */
class SearchPresenter : SearchContract.Presenter, BaseMvpPresenter<SearchContract.Model, SearchContract.View>() {
    override fun createModel(): SearchContract.Model = SearchModel()

    override fun searchGoods(keyword: String, page: Int, sort: String, direction: String) {
        mModel.searchGoods(keyword, page, sort, direction, object : HttpObserver<HomeResult>() {
            override fun onSuccess(bean: HomeResult, msg: String) {
                mView?.onSuccess(bean.goods, msg)
            }

            override fun onFailure(code: String, msg: String) {
                toast(msg)
                mView?.onFailure(msg)
            }
        })
    }
}
