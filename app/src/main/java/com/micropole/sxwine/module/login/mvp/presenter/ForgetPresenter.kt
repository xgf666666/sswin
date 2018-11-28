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
import com.micropole.sxwine.module.login.mvp.contract.ForgetContract
import com.micropole.sxwine.module.login.mvp.model.ForgetModel
import com.micropole.sxwine.utils.network.API
import com.micropole.sxwine.utils.network.HttpObserver

/**
 * Description:
 * Created by DarkHorse on 2018/5/25.
 */
class ForgetPresenter : ForgetContract.Presenter, BaseMvpPresenter<ForgetContract.Model, ForgetContract.View>() {

    override fun sendCode(mobile: String, type: String,message_code:String) {
        if (TextUtils.isEmpty(mobile)){
            mView?.onSendCodeFailure("请输入手机号")
            return
        }else if (mobile!!.length!=11){
            mView?.onSendCodeFailure("请输入正确的手机号")
            return
        }
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

    override fun resetPwd(mobile: String, password: String, password_confirmation: String, verify_code: String, message_code : String) {
        if (TextUtils.isEmpty(mobile)){
            mView?.onResetPwdFailure("请输入手机号")
            return
        }else if (mobile!!.length!=11){
            mView?.onResetPwdFailure("请输入正确的手机号")
            return
        }else if (TextUtils.isEmpty(verify_code)){
            mView?.onResetPwdFailure("请输入验证码")
            return
        }else if (verify_code!!.length!=6){
            mView?.onResetPwdFailure("请输入6位验证码")
            return
        }else if (TextUtils.isEmpty(password)){
            mView?.onResetPwdFailure("请输入新密码")
            return
        }else if (password.length<6) {
            mView?.onResetPwdFailure(Utils.getApp().getString(R.string.pwd_min))
            return
        }else if (TextUtils.isEmpty(password_confirmation)){
            mView?.onResetPwdFailure("请输入确认密码")
            return
        }else if (password!=password_confirmation){
            mView?.onResetPwdFailure("新密码和确认密码不一致")
            return
        }

        mModel.resetPwd(mobile, EncryptUtils.encryptMD5ToString(password).toLowerCase()
                , EncryptUtils.encryptMD5ToString(password_confirmation).toLowerCase()
                ,verify_code,message_code,object : HttpObserver<LoginEntity>(){
            override fun onSuccess(bean: LoginEntity, msg: String) {
                PreferencesHelper.put(API.TOKEN_SHORT,bean.token.sign_api)
                PreferencesHelper.put(API.TOKEN_LONG,bean.token.sign_login)
                PreferencesHelper.put(Constants.IS_LOGIN,true)
                mView?.onResetPwdSuccess()
                toast(msg)
            }

            override fun onFailure(code: String, msg: String) {
                mView?.onResetPwdFailure(msg)
            }

        })
    }
    override fun createModel(): ForgetContract.Model = ForgetModel()
}
