package com.micropole.sxwine.module.login.mvp.contract

import com.example.mvpframe.BaseMvpView
import com.micropole.sxwine.module.login.bean.LoginEntity
import com.micropole.sxwine.utils.network.HttpObserver

/**
 * Description:
 * Created by DarkHorse on 2018/5/25.
 */
interface ForgetContract {

    interface View : BaseMvpView {

        fun onSendCodeSuccess()

        fun onSendCodeFailure(err : String)

        fun onResetPwdSuccess()

        fun onResetPwdFailure(err : String)
    }

    interface Presenter {

        fun sendCode(mobile : String,type : String,message_code:String)

        fun resetPwd(mobile : String,password : String,password_confirmation : String,verify_code : String,message_code: String)
    }

    interface Model {

        fun sendCode(mobile : String,type : String,message_code: String,httpObserver: HttpObserver<Any>)

        fun resetPwd(mobile : String,password : String,password_confirmation : String
                     ,verify_code : String,message_code:String,httpObserver: HttpObserver<LoginEntity>)
    }
}
