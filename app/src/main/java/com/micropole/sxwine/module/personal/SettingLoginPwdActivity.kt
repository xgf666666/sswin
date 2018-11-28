package com.micropole.sxwine.module.personal

import android.content.Intent
import android.view.View
import com.darkhorse.preferencesmanager.PreferencesHelper
import com.example.mvpframe.BaseMvpActivity
import com.micropole.sxwine.R
import com.micropole.sxwine.base.hideLoadingDialog
import com.micropole.sxwine.base.initToolBar
import com.micropole.sxwine.base.showLoadingDialog
import com.micropole.sxwine.base.toast
import com.micropole.sxwine.common.Constants
import com.micropole.sxwine.module.login.LoginActivity
import com.micropole.sxwine.module.personal.Bean.UserInfoEntity
import com.micropole.sxwine.module.personal.mvp.contract.SettingLoginPwdContract
import com.micropole.sxwine.module.personal.mvp.presenter.SettingLoginPwdPresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DefaultObserver
import kotlinx.android.synthetic.main.activity_setting_login_pwd.*
import kotlinx.android.synthetic.main.item_toolbar.*
import java.util.concurrent.TimeUnit

/**
 * Created by JohnnyH on 2018/6/15.
 * 修改/设置登录密码
 */
class SettingLoginPwdActivity : BaseMvpActivity<SettingLoginPwdContract.Model,SettingLoginPwdContract.View,SettingLoginPwdPresenter>(),SettingLoginPwdContract.View, View.OnClickListener {

    override fun createPresenter(): SettingLoginPwdPresenter = SettingLoginPwdPresenter()
    override fun getLayoutId(): Int = R.layout.activity_setting_login_pwd

    override fun initView() {
        initToolBar(getString(R.string.login_pwd))
        toolbar.setNavigationOnClickListener { finish() }
        tv_send_code.setOnClickListener(this)
        btn_confirm.setOnClickListener(this)
        val userInfoEntity = PreferencesHelper[Constants.USER_INFO, UserInfoEntity()] as UserInfoEntity
        tv_phone.text= userInfoEntity.mobile
    }

    override fun initListener() {

    }

    override fun initData() {

    }

    override fun onClick(view: View?) {
        when(view!!.id){
            R.id.tv_send_code->{//获取手机验证码
                showLoadingDialog()
                mPresenter.sendCode(tv_phone.text.toString(),"4")
            }
            R.id.btn_confirm->{//确认
                val phone = tv_phone.text.toString()
                val code = ed_code.text.toString().trim()
                val new_pwd = ed_new_pwd.text.toString().trim()
                val confirm_pwd = ed_confirm_pwd.text.toString().trim()
                showLoadingDialog()
                mPresenter.alterOrSetLoginPwd(phone,new_pwd,confirm_pwd,code)
            }
        }
    }

    override fun onSendCodeSuccess(msg: String?) {
        hideLoadingDialog()
        toast(msg!!)
        //开始倒计时
        val timeCount = 60
        Observable.interval(0, 1, TimeUnit.SECONDS)
                .take((timeCount + 1).toLong())
                .map { aLong -> timeCount - aLong!! }
                .doOnSubscribe {
                    //发送数据中,将发送按钮状态设置为不可用
                    tv_send_code.isClickable=false

                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : DefaultObserver<Long>() {
                    override fun onNext(t: Long) {
                        tv_send_code.text="(" + t + "s)"
                    }

                    override fun onError(e: Throwable) {
                        tv_send_code.isClickable=true
                        tv_send_code.text=getString(R.string.tv_retry)

                    }

                    override fun onComplete() {
                        tv_send_code.isClickable=true
                        tv_send_code.text=getString(R.string.tv_resend)
                    }
                })
    }

    override fun onSendCodeFailure(err: String?) {
        hideLoadingDialog()
        toast(err!!)
    }

    override fun onAlterOrSetLoginPwdSuccess() {
        hideLoadingDialog()
        val intent = Intent(mContext, LoginActivity::class.java)
        startActivity(intent)
        finish()

    }

    override fun onAlterOrSetLoginPwdFailure(err: String?) {
        hideLoadingDialog()
        toast(err!!)
    }

}