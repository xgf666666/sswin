package com.micropole.sxwine.module.login

import android.view.View
import com.example.mvpframe.BaseMvpActivity
import com.micropole.sxwine.R
import com.micropole.sxwine.base.hideLoadingDialog
import com.micropole.sxwine.base.initToolBar
import com.micropole.sxwine.base.showLoadingDialog
import com.micropole.sxwine.base.toast
import com.micropole.sxwine.module.login.mvp.contract.ForgetContract
import com.micropole.sxwine.module.login.mvp.presenter.ForgetPresenter
import com.micropole.sxwine.widgets.SelectCountryAndAreaDialog
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DefaultObserver
import kotlinx.android.synthetic.main.activity_forget.*
import kotlinx.android.synthetic.main.item_toolbar.*
import java.util.concurrent.TimeUnit


class ForgetActivity : BaseMvpActivity<ForgetContract.Model, ForgetContract.View, ForgetPresenter>(), ForgetContract.View, View.OnClickListener {

    override fun createPresenter(): ForgetPresenter = ForgetPresenter()

    override fun getLayoutId(): Int = R.layout.activity_forget

    override fun initView() {
        initToolBar(getString(R.string.forget_password))
        toolbar.setNavigationOnClickListener{
            finish()
        }
        tv_send_code.setOnClickListener(this)
        btn_confirm.setOnClickListener(this)

    }

    override fun initData() {
    }

    override fun initListener() {

    }

    private var mCountryAreaType : String? ="86"
    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.tv_send_code->{//手机号
                val phone = ed_phone.text.toString().trim()
                showLoadingDialog()
                mPresenter.sendCode(phone,"3",mCountryAreaType!!)
            }
            R.id.btn_confirm->{
                val phone = ed_phone.text.toString().trim()
                val code = ed_code.text.toString().trim()
                val new_pwd = ed_new_pwd.text.toString().trim()
                val confirm_pwd = ed_confirm_pwd.text.toString().trim()
                showLoadingDialog()
                mPresenter.resetPwd(phone,new_pwd,confirm_pwd,code,mCountryAreaType!!)
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

    override fun onSendCodeFailure(err: String) {
        hideLoadingDialog()
        toast(err!!)
    }

    override fun onResetPwdSuccess() {
        finish()
    }

    override fun onResetPwdFailure(err: String) {
        hideLoadingDialog()
        toast(err!!)
    }



}
