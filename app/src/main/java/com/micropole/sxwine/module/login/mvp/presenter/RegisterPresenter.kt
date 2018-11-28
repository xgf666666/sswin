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
import com.micropole.sxwine.module.login.mvp.contract.RegisterContract
import com.micropole.sxwine.module.login.mvp.model.RegisterModel
import com.micropole.sxwine.module.personal.Bean.UserInfoEntity
import com.micropole.sxwine.utils.network.API
import com.micropole.sxwine.utils.network.HttpObserver

/**
 * Created by JohnnyH on 2018/6/6.
 */
class RegisterPresenter : BaseMvpPresenter<RegisterContract.Model, RegisterContract.View>(), RegisterContract.Presenter {

    override fun getUserInfo() {
        mModel.getUserInfo(object : HttpObserver<UserInfoEntity>(){
            override fun onSuccess(bean: UserInfoEntity, msg: String) {
                PreferencesHelper.put(Constants.USER_INFO,bean)
                mView?.onRegisterSuccess()
            }

            override fun onFailure(code: String, msg: String) {
                mView?.onRegisterFailure(msg)
            }

        })
    }

    override fun sendCode(mobile: String?, type: String?, message_code : String?) {
        if (TextUtils.isEmpty(mobile)) {
            mView?.onSendCodeFailure(Utils.getApp().getString(R.string.input_phone))
            return
        } /*else if (mobile!!.length<10) {
            mView?.onSendCodeFailure(Utils.getApp().getString(R.string.input_phone))
            return
        }*/
        mModel.sendCode(mobile, type,message_code, object : HttpObserver<Any>() {
            override fun onSuccess(bean: Any, msg: String) {
                mView?.onSendCodeSuccess(msg)
            }

            override fun onFailure(code: String, msg: String) {
                mView?.onSendCodeFailure(msg)
            }

        })
    }

    override fun register(mobile: String?, password: String?, password_confirmation: String?, verify_code: String?, recommend_code: String?,
                          credentials_type: String?,credentials_no: String?,real_name: String?,message_code: String?) {
        if (TextUtils.isEmpty(mobile)) {
            mView?.onRegisterFailure(Utils.getApp().getString(R.string.input_phone))
            return
        } /*else if (mobile!!.length<10) {
            mView?.onRegisterFailure(Utils.getApp().getString(R.string.input_phone))
            return
        }*/ else if (TextUtils.isEmpty(verify_code)) {
            mView?.onRegisterFailure(Utils.getApp().getString(R.string.verification_code_hint))
            return
        } else if (verify_code!!.length != 6) {
            mView?.onRegisterFailure(Utils.getApp().getString(R.string.verification_code_hint))
            return
        }else if(TextUtils.isEmpty(real_name)){
            mView?.onRegisterFailure(Utils.getApp().getString(R.string.input_name))
            return
        }else if(TextUtils.isEmpty(credentials_no)){
            mView?.onRegisterFailure(Utils.getApp().getString(R.string.input_document_code))
            return
        } else if (TextUtils.isEmpty(password)) {
            mView?.onRegisterFailure(Utils.getApp().getString(R.string.input_new_pwd))
            return
        } else if (password!!.length<6) {
            mView?.onRegisterFailure(Utils.getApp().getString(R.string.pwd_min))
            return
        }else if (TextUtils.isEmpty(password_confirmation)) {
            mView?.onRegisterFailure(Utils.getApp().getString(R.string.input_confirm_pwd))
            return
        } else if (password != password_confirmation) {
            mView?.onRegisterFailure(Utils.getApp().getString(R.string.two_pwd_err))
            return
        }

        mModel.register(mobile, EncryptUtils.encryptMD5ToString(password).toLowerCase()
                , EncryptUtils.encryptMD5ToString(password_confirmation).toLowerCase()
                , verify_code, recommend_code,credentials_type,credentials_no,real_name,message_code, object : HttpObserver<LoginEntity>() {
            override fun onSuccess(bean: LoginEntity, msg: String) {
                PreferencesHelper.put(API.TOKEN_SHORT, bean.token.sign_api)
                PreferencesHelper.put(API.TOKEN_LONG, bean.token.sign_login)
                PreferencesHelper.put(Constants.IS_LOGIN, true)
                getUserInfo()
                toast(msg)
            }

            override fun onFailure(code: String, msg: String) {
                mView?.onRegisterFailure(msg)
            }

        })
    }


    override fun createModel(): RegisterContract.Model = RegisterModel()
}