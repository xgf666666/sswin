package com.micropole.sxwine.module.personal.mvp.presenter

import com.example.mvpframe.BaseMvpPresenter
import com.micropole.sxwine.module.personal.Bean.MyWalletEntity
import com.micropole.sxwine.module.personal.mvp.contract.MyWalletContract
import com.micropole.sxwine.module.personal.mvp.model.MyWalletModel
import com.micropole.sxwine.utils.network.HttpObserver

/**
 * Created by JohnnyH on 2018/6/13.
 */
class MyWalletPresenter : BaseMvpPresenter<MyWalletContract.Model,MyWalletContract.View>(),MyWalletContract.Presenter {

    override fun loadData() {
        mModel.loadData(object : HttpObserver<MyWalletEntity>(){
            override fun onSuccess(bean: MyWalletEntity, msg: String) {
                mView?.setData(bean)
            }

            override fun onFailure(code: String, msg: String) {
                mView?.onDataFailure(msg)
            }

        })
    }

    override fun createModel(): MyWalletContract.Model = MyWalletModel()
}