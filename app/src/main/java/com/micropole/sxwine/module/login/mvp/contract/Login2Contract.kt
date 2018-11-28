package com.micropole.sxwine.module.login.mvp.contract

import com.example.mvpframe.BaseMvpView
import com.micropole.sxwine.module.login.bean.LoginEntity
import com.micropole.sxwine.module.personal.Bean.UserInfoEntity
import com.micropole.sxwine.utils.network.HttpObserver

/**
 * Description:
 * Created by DarkHorse on 2018/5/24.
 */
interface Login2Contract {

    interface View : BaseMvpView {
        fun onLoginSuccess()
        fun onLoginFailure(err : String)
    }

    interface Presenter {
        fun pwdLogin(mobile : String,password : String)

        fun getUserInfo()
    }

    interface Model {
        fun pwdLogin(mobile : String,password : String,httpObserver: HttpObserver<LoginEntity>)

        fun getUserInfo(httpObserver: HttpObserver<UserInfoEntity>)
    }
}
