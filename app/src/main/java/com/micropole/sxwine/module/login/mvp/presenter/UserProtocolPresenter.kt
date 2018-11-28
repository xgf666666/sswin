package com.micropole.sxwine.module.login.mvp.presenter

import com.example.mvpframe.BaseMvpPresenter
import com.micropole.sxwine.base.toast
import com.micropole.sxwine.module.login.bean.UserProtocolEntity
import com.micropole.sxwine.module.login.mvp.contract.UserProtocolContract
import com.micropole.sxwine.module.login.mvp.model.UserProtocolModel
import com.micropole.sxwine.utils.network.HttpObserver

/**
 * Created by JohnnyH on 2018/9/17.
 */
class UserProtocolPresenter : BaseMvpPresenter<UserProtocolContract.Model,UserProtocolContract.View>(),UserProtocolContract.Presenter {
    override fun getProtocol() {
        mModel.getProtocol(object : HttpObserver<UserProtocolEntity>(){
            override fun onSuccess(bean: UserProtocolEntity, msg: String) {
                mView?.onGetProtocolSuccess(bean)
            }

            override fun onFailure(code: String, msg: String) {
                toast(msg)
                mView?.onGetProtocolFailure()
            }

        })
    }

    override fun createModel(): UserProtocolContract.Model = UserProtocolModel()
}