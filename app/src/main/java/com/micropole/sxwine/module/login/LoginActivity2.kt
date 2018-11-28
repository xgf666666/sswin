package com.micropole.sxwine.module.login

import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import cn.jpush.android.api.JPushInterface
import com.example.mvpframe.BaseMvpActivity
import com.micropole.sxwine.MainActivity
import com.micropole.sxwine.R
import com.micropole.sxwine.R.id.*
import com.micropole.sxwine.base.hideLoadingDialog
import com.micropole.sxwine.base.initToolBar
import com.micropole.sxwine.base.showLoadingDialog
import com.micropole.sxwine.base.toast
import com.micropole.sxwine.module.login.mvp.contract.Login2Contract
import com.micropole.sxwine.module.login.mvp.presenter.Login2Presenter
import kotlinx.android.synthetic.main.activity_login2.*
import kotlinx.android.synthetic.main.item_toolbar.*

class LoginActivity2 : BaseMvpActivity<Login2Contract.Model, Login2Contract.View, Login2Presenter>(), View.OnClickListener, Login2Contract.View {

    override fun createPresenter(): Login2Presenter = Login2Presenter()

    override fun getLayoutId(): Int = R.layout.activity_login2

    override fun initView() {
        initToolBar(getString(R.string.tv_pwd_login))
        toolbar.setNavigationOnClickListener {
            finish()
        }
        iv_see_pwd.setOnClickListener(this)
        btn_login.setOnClickListener(this)
        tv_code_login.setOnClickListener(this)
        tv_forget_pwd.setOnClickListener(this)
    }

    override fun initData() {

    }

    override fun initListener() {

    }


    private var isSeePwd: Boolean = false //登录密码是否可见
    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.iv_see_pwd -> {//密码是否可见
                isSeePwd = !isSeePwd
                changeUI(isSeePwd)
            }
            R.id.btn_login -> {//登录
                val phone = ed_phone.text.toString().trim()
                val pwd = ed_pwd.text.toString().trim()
                showLoadingDialog()
                mPresenter.pwdLogin(phone, pwd)
            }
            R.id.tv_code_login -> {//短信验证登录
                val intent = Intent(mContext, CodeLoginActivity::class.java)
                startActivity(intent)
            }
            R.id.tv_forget_pwd -> {//忘记密码
                val intent = Intent(mContext, ForgetActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun changeUI(isVisible: Boolean) {
        if (isVisible) {//可见
            iv_see_pwd.setImageResource(R.mipmap.login_eyes)
            ed_pwd.transformationMethod = HideReturnsTransformationMethod.getInstance()
        } else {//不可见
            iv_see_pwd.setImageResource(R.mipmap.login_closed_eyes)
            ed_pwd.transformationMethod = PasswordTransformationMethod.getInstance()
        }
    }

    override fun onLoginSuccess() {
        hideLoadingDialog()
        val phone = ed_phone.text.toString().trim()
        JPushInterface.setAlias(mContext, 1, phone)
        val intent = Intent(mContext, MainActivity::class.java)
        val bundle = Bundle()
        bundle.putBoolean("isLogin", true)
        intent.putExtra("data", bundle)
        startActivity(intent)
        finish()
    }

    override fun onLoginFailure(err: String) {
        hideLoadingDialog()
        toast(err)
    }

}
