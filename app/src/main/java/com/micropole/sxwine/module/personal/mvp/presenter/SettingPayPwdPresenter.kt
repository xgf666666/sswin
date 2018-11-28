package com.micropole.sxwine.module.personal.mvp.presenter

import android.text.TextUtils
import com.blankj.utilcode.util.EncryptUtils
import com.blankj.utilcode.util.Utils
import com.example.mvpframe.BaseMvpPresenter
import com.micropole.sxwine.R
import com.micropole.sxwine.base.toast
import com.micropole.sxwine.module.personal.mvp.contract.SettingPayPwdContract
import com.micropole.sxwine.module.personal.mvp.model.SettingPayPwdModel
import com.micropole.sxwine.utils.network.HttpObserver

/**
 * Created by JohnnyH on 2018/6/15.
 */
class SettingPayPwdPresenter : BaseMvpPresenter<SettingPayPwdContract.Model, SettingPayPwdContract.View>(), SettingPayPwdContract.Presenter {

    override fun createModel(): SettingPayPwdContract.Model = SettingPayPwdModel()

    override fun sendCode(mobile: String?, type: String?) {
        if (TextUtils.isEmpty(mobile)) {
            mView?.onSendCodeFailure(Utils.getApp().getString(R.string.input_phone))
            return
        } /*else if (mobile!!.length<10) {
            mView?.onSendCodeFailure(Utils.getApp().getString(R.string.input_phone))
            return
        }*/
        mModel.sendCode(mobile, type, object : HttpObserver<Any>() {
            override fun onSuccess(bean: Any, msg: String) {
                mView?.onSendCodeSuccess(msg)
            }

            override fun onFailure(code: String, msg: String) {
                mView?.onSendCodeFailure(msg)
            }
        })
    }

    override fun alterOrSetPayPwd(mobile: String?, password: String?, password_confirmation: String?, verify_code: String?) {
        if (TextUtils.isEmpty(mobile)) {
            mView?.onAlterOrSetPayPwdFailure(Utils.getApp().getString(R.string.input_phone))
            return
        } /*else if (mobile!!.length<10) {
            mView?.onAlterOrSetPayPwdFailure(Utils.getApp().getString(R.string.input_phone))
            return
        }*/ else if (TextUtils.isEmpty(verify_code)) {
            mView?.onAlterOrSetPayPwdFailure(Utils.getApp().getString(R.string.verification_code_hint))
            return
        } else if (verify_code!!.length != 6) {
            mView?.onAlterOrSetPayPwdFailure(Utils.getApp().getString(R.string.verification_code_hint))
            return
        } else if (TextUtils.isEmpty(password)) {
            mView?.onAlterOrSetPayPwdFailure(Utils.getApp().getString(R.string.input_new_pwd))
            return
        } else if (password!!.length<6) {
            mView?.onAlterOrSetPayPwdFailure(Utils.getApp().getString(R.string.pwd_min))
            return
        }else if (TextUtils.isEmpty(password_confirmation)) {
            mView?.onAlterOrSetPayPwdFailure(Utils.getApp().getString(R.string.input_confirm_pwd))
            return
        } else if (password != password_confirmation) {
            mView?.onAlterOrSetPayPwdFailure(Utils.getApp().getString(R.string.two_pwd_err))
            return
        }

        mModel.alterOrSetPayPwd(mobile, EncryptUtils.encryptMD5ToString(password).toLowerCase()
                , EncryptUtils.encryptMD5ToString(password_confirmation).toLowerCase()
                , verify_code, object : HttpObserver<Any>() {
            override fun onSuccess(bean: Any, msg: String) {
                mView?.onAlterOrSetPayPwdSuccess()
                toast(msg)
            }

            override fun onFailure(code: String, msg: String) {
                mView?.onAlterOrSetPayPwdFailure(msg)
            }

        })
    }
}