package com.micropole.sxwine.module.login.mvp.presenter

import android.text.TextUtils
import com.blankj.utilcode.util.Utils
import com.darkhorse.preferencesmanager.PreferencesHelper
import com.example.mvpframe.BaseMvpPresenter
import com.micropole.sxwine.R
import com.micropole.sxwine.base.toast
import com.micropole.sxwine.common.Constants
import com.micropole.sxwine.module.login.bean.LoginEntity
import com.micropole.sxwine.module.login.mvp.contract.CodeLoginContract
import com.micropole.sxwine.module.login.mvp.model.CodeLoginModel
import com.micropole.sxwine.module.personal.Bean.UserInfoEntity
import com.micropole.sxwine.utils.network.API
import com.micropole.sxwine.utils.network.HttpObserver

/**
 * Created by JohnnyH on 2018/6/7.
 */
class CodeLoginPresenter : BaseMvpPresenter<CodeLoginContract.Model,CodeLoginContract.View>(),CodeLoginContract.Presenter {

    override fun createModel(): CodeLoginContract.Model = CodeLoginModel()

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

    override fun sendCode(mobile: String?, type: String?, message_code: String?) {

        if (TextUtils.isEmpty(mobile)){
            mView?.onSendCodeFailure(Utils.getApp().getString(R.string.input_phone))
            return
        }/*else if (mobile!!.length<10){
            mView?.onSendCodeFailure(Utils.getApp().getString(R.string.input_phone))
            return
        }*/

        mModel.sendCode(mobile,type,message_code,object : HttpObserver<Any>(){
            override fun onSuccess(bean: Any, msg: String) {
                toast(msg)
                mView?.onSendCodeSuccess()
            }

            override fun onFailure(code: String, msg: String) {
                mView?.onSendCodeFailure(msg)
            }

        })
    }

    override fun codeLogin(mobile: String?, verify_code: String?,message_code: String?) {
        if (TextUtils.isEmpty(mobile)){
            mView?.onSendCodeFailure(Utils.getApp().getString(R.string.input_phone))
            return
        }/*else if (mobile!!.length<10){
            mView?.onSendCodeFailure(Utils.getApp().getString(R.string.input_phone))
            return
        }*/else if (TextUtils.isEmpty(verify_code)){
            mView?.onSendCodeFailure(Utils.getApp().getString(R.string.verification_code_hint))
            return
        }else if (verify_code!!.length!=6){
            mView?.onSendCodeFailure(Utils.getApp().getString(R.string.verification_code_hint))
            return
        }
        mModel.codeLogin(mobile,verify_code,message_code,object : HttpObserver<LoginEntity>(){
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
}