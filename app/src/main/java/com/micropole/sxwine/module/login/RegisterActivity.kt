package com.micropole.sxwine.module.login

import android.content.Intent
import android.text.InputFilter
import android.text.Spanned
import android.util.Log
import android.view.View
import com.example.mvpframe.BaseMvpActivity
import com.micropole.sxwine.MainActivity
import com.micropole.sxwine.R
import com.micropole.sxwine.base.hideLoadingDialog
import com.micropole.sxwine.base.initToolBar
import com.micropole.sxwine.base.showLoadingDialog
import com.micropole.sxwine.base.toast
import com.micropole.sxwine.module.login.mvp.contract.RegisterContract
import com.micropole.sxwine.module.login.mvp.presenter.RegisterPresenter
import com.micropole.sxwine.widgets.SelectCountryAndAreaDialog
import com.micropole.sxwine.widgets.SelectDocumentDialog
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DefaultObserver
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.item_toolbar.*
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern

/**
 * Created by JohnnyH on 2018/6/6.
 */
class RegisterActivity : BaseMvpActivity<RegisterContract.Model,RegisterContract.View,RegisterPresenter>(), View.OnClickListener,RegisterContract.View {


    override fun createPresenter(): RegisterPresenter = RegisterPresenter()

    override fun getLayoutId(): Int = R.layout.activity_register

    override fun initView() {
        initToolBar(getString(R.string.register))
        toolbar.setNavigationOnClickListener {
            finish()
        }
        tv_send_code.setOnClickListener(this)
        btn_confirm.setOnClickListener(this)
        ll_country_area.setOnClickListener(this)
        ll_document.setOnClickListener(this)
        iv_check.setOnClickListener(this)
        tv_check.setOnClickListener(this)

    }

    override fun initData() {

    }

    override fun initListener() {
        val typeFilter = object : InputFilter {
           override fun filter(source: CharSequence, start: Int, end: Int, dest: Spanned, dstart: Int, dend: Int): CharSequence? {
                val p = Pattern.compile("[a-zA-Z|\u4e00-\u9fa5]+")
                val m = p.matcher(source.toString())
                return if (!m.matches()) "" else null
            }
        }
        ed_name.filters= arrayOf(typeFilter)

    }

    private var mCountryAreaType:String = "6"
    private var mDocumentType:String = "2"
    private var mIsCheck : Boolean =true
    override fun onClick(view: View?) {
        when(view!!.id){
            R.id.ll_country_area->{//国家/地区
                val selectCountryAndAreaDialog = SelectCountryAndAreaDialog(mContext)
                selectCountryAndAreaDialog.setSelectState(mCountryAreaType)
                selectCountryAndAreaDialog.setOnSelectStateListener {
                    mCountryAreaType=it
                    when(it){
                        "6"->{
                            tv_country_area.text=getString(R.string.Malaysia)
                        }
                        "86"->{
                            tv_country_area.text=getString(R.string.China)
                        }
                    }
                }
                selectCountryAndAreaDialog.show()

            }
            R.id.ll_document->{//证件
                val selectDocumentDialog = SelectDocumentDialog(mContext)
                selectDocumentDialog.setSelectState(mDocumentType)
                selectDocumentDialog.setOnSelectStateListener {
                    mDocumentType=it
                    Log.e("Tag","it="+it)
                    when(it){
                        "1"->{
                            tv_document.text=getString(R.string.Id_Card)
                        }
                        "2"->{
                            tv_document.text=getString(R.string.Passport)
                        }
                    }
                }
                selectDocumentDialog.show()

            }
            R.id.tv_send_code->{//发送验证码
                showLoadingDialog()
                mPresenter.sendCode(ed_phone.text.toString().trim(),"1",mCountryAreaType)
            }
            R.id.btn_confirm->{//确认
                val phone = ed_phone.text.toString().trim()
                val code=ed_code.text.toString().trim()
                val new_pwd = ed_new_pwd.text.toString().trim()
                val confirm_pwd = ed_confirm_pwd.text.toString().trim()
                val invite_code = ed_invite_code.text.toString().trim()
                val document_code = ed_document_code.text.toString().trim()
                val name = ed_name.text.toString().trim()
                if (!mIsCheck){
                    toast(getString(R.string.tv_check_protocol))
                }
                showLoadingDialog()
                mPresenter.register(phone,new_pwd,confirm_pwd,code,invite_code,mDocumentType,
                        document_code,name,mCountryAreaType)
            }
            R.id.iv_check->{
                mIsCheck=!mIsCheck
                if (mIsCheck){
                    iv_check.setImageResource(R.mipmap.home_btn_confirm_red)
                }else{
                    iv_check.setImageResource(R.mipmap.home_btn_input)
                }
            }
            R.id.tv_check->{//协议
                val intent = Intent(mContext, UserProtocolActivity::class.java)
                startActivity(intent)
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

    override fun onRegisterSuccess() {
        val intent = Intent(mContext, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onRegisterFailure(err: String?) {
        hideLoadingDialog()
        toast(err!!)
    }
}