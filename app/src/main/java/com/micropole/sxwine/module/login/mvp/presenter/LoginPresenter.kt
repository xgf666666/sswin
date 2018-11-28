package com.micropole.sxwine.module.login.mvp.presenter

import com.example.mvpframe.BaseMvpPresenter
import com.micropole.sxwine.bean.LoginResult
import com.micropole.sxwine.module.login.mvp.contract.LoginContract
import com.micropole.sxwine.module.login.mvp.model.LoginModel
import com.micropole.sxwine.utils.network.HttpObserver

/**
 * Description:
 * Created by DarkHorse on 2018/5/24.
 */
class LoginPresenter : LoginContract.Presenter, BaseMvpPresenter<LoginContract.Model, LoginContract.View>() {

    override fun login(phone: String, pwd: String) {
        mModel.login(phone, pwd, object : HttpObserver<LoginResult>() {

            override fun onSuccess(bean: LoginResult, msg: String) {
                mView?.onSuccess(bean)
            }

            override fun onFailure(code: String, msg: String) {
                mView?.onFailure(msg)
            }

        })
    }

    override fun createModel(): LoginContract.Model = LoginModel()
}
