package com.micropole.sxwine.module.login.mvp.presenter

import android.text.TextUtils
import com.blankj.utilcode.util.EncryptUtils
import com.blankj.utilcode.util.Utils
import com.darkhorse.preferencesmanager.PreferencesHelper
import com.example.mvpframe.BaseMvpPresenter
import com.micropole.sxwine.R
import com.micropole.sxwine.base.toast
import com.micropole.sxwine.common.Constants
import com.micropole.sxwine.module.login.bean.LoginEntity
import com.micropole.sxwine.module.login.mvp.contract.Login2Contract
import com.micropole.sxwine.module.login.mvp.model.Login2Model
import com.micropole.sxwine.module.personal.Bean.UserInfoEntity
import com.micropole.sxwine.utils.network.API
import com.micropole.sxwine.utils.network.HttpObserver

/**
 * Description:
 * Created by DarkHorse on 2018/5/24.
 */
class Login2Presenter : Login2Contract.Presenter, BaseMvpPresenter<Login2Contract.Model, Login2Contract.View>() {

    override fun getUserInfo() {
        mModel.getUserInfo(object : HttpObserver<UserInfoEntity>(){
            override fun onSuccess(bean: UserInfoEntity, msg: String) {
                PreferencesHelper.put(Constants.USER_INFO,bean)
                mView?.onLoginSuccess()
            }

            override fun onFailure(code: String, msg: String) {
                mView?.onLoginFailure(msg)
            }

        })
    }

    override fun pwdLogin(mobile: String, password: String) {
        if (TextUtils.isEmpty(mobile)) {
            mView?.onLoginFailure(Utils.getApp().getString(R.string.input_phone))
            return
        } /*else if (mobile.length<10) {
            mView?.onLoginFailure(Utils.getApp().getString(R.string.input_phone))
            return
        }*/ else if (TextUtils.isEmpty(password)) {
            mView?.onLoginFailure(Utils.getApp().getString(R.string.input_login_pwd))
            return
        }else if (password.length<6) {
            mView?.onLoginFailure(Utils.getApp().getString(R.string.pwd_min))
            return
        }
        mModel.pwdLogin(mobile, EncryptUtils.encryptMD5ToString(password).toLowerCase(), object : HttpObserver<LoginEntity>() {
            override fun onSuccess(bean: LoginEntity, msg: String) {
                PreferencesHelper.put(API.TOKEN_LONG, bean.token.sign_login)
                PreferencesHelper.put(API.TOKEN_SHORT, bean.token.sign_api)
                PreferencesHelper.put(Constants.IS_LOGIN, true)
                getUserInfo()
                toast(msg)
            }

            override fun onFailure(code: String, msg: String) {
                mView?.onLoginFailure(msg)
            }

        })
    }

    override fun createModel(): Login2Contract.Model = Login2Model()
}
