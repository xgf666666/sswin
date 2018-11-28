package com.micropole.sxwine.module.login.mvp.presenter

import com.example.mvpframe.BaseMvpPresenter
import com.micropole.sxwine.module.login.bean.ADEntity
import com.micropole.sxwine.module.login.mvp.contract.SplashContract
import com.micropole.sxwine.module.login.mvp.model.SplashModel
import com.micropole.sxwine.utils.network.HttpObserver

/**
 * Created by JohnnyH on 2018/8/28.
 */
class SplashPresenter : BaseMvpPresenter<SplashContract.Model,SplashContract.View>(),SplashContract.Presenter {
    override fun loadData() {
        mModel.loadData(object : HttpObserver<ADEntity>(){
            override fun onSuccess(bean: ADEntity, msg: String) {
                mView?.onGetAdSuccess(bean)
            }

            override fun onFailure(code: String, msg: String) {
                mView?.onGetAdFailure(msg)
            }

        })
    }

    override fun createModel(): SplashContract.Model = SplashModel()
}