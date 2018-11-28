package com.micropole.sxwine.module.login.mvp.contract

import com.example.mvpframe.BaseMvpView
import com.micropole.sxwine.bean.LoginResult
import com.micropole.sxwine.utils.network.HttpObserver

/**
 * Description:
 * Created by DarkHorse on 2018/5/24.
 */
interface LoginContract {
    interface Model {
        fun login(phone: String, pwd: String, observer: HttpObserver<LoginResult>)
    }

    interface View : BaseMvpView {
        fun onSuccess(bean: LoginResult)

        fun onFailure(msg: String)
    }

    interface Presenter {
        fun login(phone: String, pwd: String)
    }
}
