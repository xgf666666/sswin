package com.micropole.sxwine.module.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import cn.jpush.android.api.JPushInterface
import com.example.mvpframe.BaseMvpActivity
import com.micropole.sxwine.MainActivity
import com.micropole.sxwine.R
import com.micropole.sxwine.base.hideLoadingDialog
import com.micropole.sxwine.base.initToolBar
import com.micropole.sxwine.base.showLoadingDialog
import com.micropole.sxwine.base.toast
import com.micropole.sxwine.module.login.mvp.contract.CodeLoginContract
import com.micropole.sxwine.module.login.mvp.presenter.CodeLoginPresenter
import com.micropole.sxwine.widgets.SelectCountryAndAreaDialog
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DefaultObserver
import kotlinx.android.synthetic.main.activity_code_login.*
import kotlinx.android.synthetic.main.item_toolbar.*
import java.util.concurrent.TimeUnit

/**
 * Created by JohnnyH on 2018/6/7.
 */
class CodeLoginActivity : BaseMvpActivity<CodeLoginContract.Model, CodeLoginContract.View, CodeLoginPresenter>(), CodeLoginContract.View, View.OnClickListener {

    override fun createPresenter(): CodeLoginPresenter = CodeLoginPresenter()

    override fun getLayoutId(): Int = R.layout.activity_code_login

    override fun initView() {
        initToolBar(getString(R.string.msg_code_login))
        toolbar.setNavigationOnClickListener {
            finish()
        }
        tv_send_code.setOnClickListener(this)
        btn_login.setOnClickListener(this)
        tv_pwd_login.setOnClickListener(this)
        tv_forget_pwd.setOnClickListener(this)
    }

    override fun initListener() {
        tv_pwd_login
    }

    override fun initData() {

    }

    private var mCountryAreaType : String ? = "86"
    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.tv_send_code -> {//发送验证码
                val phone = ed_phone.text.toString().trim()
                showLoadingDialog()
                mPresenter.sendCode(phone, "2",mCountryAreaType)
            }
            R.id.btn_login -> {//登录
                val phone = ed_phone.text.toString().trim()
                val code = ed_invite_code.text.toString().trim()
                showLoadingDialog()
                mPresenter.codeLogin(phone, code,mCountryAreaType)
            }
            R.id.tv_pwd_login -> {//账号密码登录
                val intent = Intent(mContext, LoginActivity2::class.java)
                startActivity(intent)
            }
            R.id.tv_forget_pwd -> {//忘记密码
                val intent = Intent(mContext, ForgetActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onSendCodeSuccess() {
        hideLoadingDialog()
        //开始倒计时
        val timeCount = 60
        Observable.interval(0, 1, TimeUnit.SECONDS)
                .take((timeCount + 1).toLong())
                .map { aLong -> timeCount - aLong!! }
                .doOnSubscribe {
                    //发送数据中,将发送按钮状态设置为不可用
                    tv_send_code.isClickable = false

                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : DefaultObserver<Long>() {
                    override fun onNext(t: Long) {
                        tv_send_code.text = "(" + t + "s)"
                    }

                    override fun onError(e: Throwable) {
                        tv_send_code.isClickable = true
                        tv_send_code.text = getString(R.string.tv_retry)
                    }

                    override fun onComplete() {
                        tv_send_code.isClickable = true
                        tv_send_code.text = getString(R.string.tv_resend)
                    }
                })
    }

    override fun onSendCodeFailure(err: String?) {
        hideLoadingDialog()
        toast(err!!)
    }

    override fun onLoginSuccess() {
        val phone = ed_phone.text.toString().trim()
        JPushInterface.setAlias(mContext, 1, phone)
        val intent = Intent(mContext, MainActivity::class.java)
        val bundle = Bundle()
        bundle.putBoolean("isLogin", true)
        intent.putExtra("data", bundle)

        startActivity(intent)
        finish()
    }

    override fun onLoginFailure(err: String?) {
        hideLoadingDialog()
        toast(err!!)
    }
}