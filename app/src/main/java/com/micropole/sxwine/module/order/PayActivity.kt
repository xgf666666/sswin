package com.micropole.sxwine.module.order

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Parcelable
import android.text.TextUtils
import android.view.View
import com.blankj.utilcode.util.EncryptUtils
import com.braintreepayments.api.dropin.DropInActivity
import com.braintreepayments.api.dropin.DropInRequest
import com.braintreepayments.api.dropin.DropInResult
import com.example.mvpframe.BaseMvpActivity
import com.micropole.tanglong.PayWebViewActivity
import com.micropole.tanglong.WebViewActivity
import com.micropole.sxwine.R
import com.micropole.sxwine.R.id.*
import com.micropole.sxwine.base.*
import com.micropole.sxwine.bean.PayResult
import com.micropole.sxwine.module.order.mvp.contract.PayContract
import com.micropole.sxwine.module.order.mvp.presenter.PayPresenter
import com.micropole.sxwine.module.personal.Bean.CheckPayPwdEntity
import com.micropole.sxwine.module.personal.SettingPayPwdActivity
import com.micropole.sxwine.widgets.InputPwdDialog
import com.micropole.sxwine.widgets.MarqueeView
import kotlinx.android.synthetic.main.activity_earnings_withcdraw2.*
import kotlinx.android.synthetic.main.activity_pay.*

class PayActivity : BaseMvpActivity<PayContract.Model, PayContract.View, PayPresenter>(), PayContract.View, View.OnClickListener {

    override fun createPresenter(): PayPresenter = PayPresenter()

    override fun getLayoutId(): Int = R.layout.activity_pay

    private var payType: Int = 0

    private lateinit var mOrderId: String

    override fun initView() {
        initToolBar(getString(R.string.pay))
        select(2)
        tv_sum_pay.text = "RM${getBundle()!!.getString("pay_amount")}"
    }

    override fun initListener() {
        btn_confirm_pay.setOnClickListener(this)
        btn_pay_1.setOnClickListener(this)
        btn_pay_2.setOnClickListener(this)
        btn_pay_3.setOnClickListener(this)
        btn_pay_4.setOnClickListener(this)
        btn_alipay.setOnClickListener(this)
        btn_wechat.setOnClickListener(this)
    }

    override fun initData() {
        mOrderId = getBundle()!!.getString("order_id")
    }

    override fun onClick(v: View?) {
        when (v) {
            btn_confirm_pay -> {
                when (payType) {
                    1 -> pay1()
                    2 -> pay2()
                    3 -> pay3()
                    4 -> pay4()
                }
            }
            btn_pay_1 -> select(1)
            btn_pay_2 -> select(2)
            btn_pay_3 -> select(3)
            btn_pay_4 -> select(4)
            btn_alipay->select(5)
            btn_wechat->select(6)
        }
    }

    private fun showPayPwdDialog() {
        val inputPwdDialog = InputPwdDialog(mContext)
        inputPwdDialog.setOnPswDialogClickListener(object : InputPwdDialog.OnPswDialogClickListener {
            override fun onConfirm(password: String?) {
                showLoadingDialog()
                mPresenter.balancePay(mOrderId, EncryptUtils.encryptMD5ToString(password).toLowerCase())
                inputPwdDialog.dismiss()
            }

            override fun onLinkClick() {
            }
        })

        inputPwdDialog.setOnForgetClickListener {
            val intent = Intent(mContext, SettingPayPwdActivity::class.java)
            startActivity(intent)
            inputPwdDialog.dismiss()
        }
        inputPwdDialog.show()
    }

    fun select(type: Int) {
        when (type) {
            1 -> {
                cb_choice1.isChecked = true
                cb_choice2.isChecked = false
                cb_choice3.isChecked = false
                cb_choice4.isChecked = false
                payType = 1
            }
            2 -> {
                cb_choice1.isChecked = false
                cb_choice2.isChecked = true
                cb_choice3.isChecked = false
                cb_choice4.isChecked = false
                payType = 2
            }
            3 -> {

                cb_choice1.isChecked = false
                cb_choice2.isChecked = false
                cb_choice3.isChecked = true
                cb_choice4.isChecked = false
                payType = 3
            }
            4 -> {
                cb_choice1.isChecked = false
                cb_choice2.isChecked = false
                cb_choice3.isChecked = false
                cb_choice4.isChecked = true
                cb_wechat.isChecked=false
                cb_alipay.isChecked=false
                payType = 4
            }
            5->{
                cb_alipay.isChecked=true
                cb_choice4.isChecked=false
                cb_wechat.isChecked=false
                payType = 5
            }
            6->{
                cb_alipay.isChecked=false
                cb_choice4.isChecked=false
                cb_wechat.isChecked=true
                payType = 6
            }
        }
    }

    fun pay1() {
        showLoadingDialog()
        mPresenter.pay(mOrderId, "PAYPAL")
    }

    fun pay2() {
        showLoadingDialog()
        mPresenter.pay(mOrderId, "EGHL")
    }

    fun pay3() {
        toast("pay3")
    }

    fun pay4() {
        mPresenter.checkPayPwd()
    }

    override fun onSuccess(result: PayResult) {
        hideLoadingDialog()
        when (payType) {
            1 -> {
//                val cart = Cart.newBuilder()
//                        .setCurrencyCode("USD")
//                        .setTotalPrice("150.00")
//                        .addLineItem(LineItem.newBuilder()
//                                .setCurrencyCode("USD")
//                                .setDescription("Description")
//                                .setQuantity("1")
//                                .setUnitPrice("150.00")
//                                .setTotalPrice("150.00")
//                                .build())
//                        .build()
                val dropInRequest = DropInRequest()
                        .clientToken(result.client_token)
//                        .amount("1.00")
//                        .collectDeviceData(Settings.shouldCollectDeviceData(this))
//                        .requestThreeDSecureVerification(Settings.isThreeDSecureEnabled(this))
//                        .androidPayCart(cart)
//                        .androidPayShippingAddressRequired(Settings.isAndroidPayShippingAddressRequired(this))
//                        .androidPayPhoneNumberRequired(Settings.isAndroidPayPhoneNumberRequired(this))

                // REQUEST_CODE is arbitrary and is only used within this activity
                startActivityForResult(dropInRequest.getIntent(this), 1)
            }
            2 -> {
                val intent = Intent(this, PayWebViewActivity::class.java)
                intent.putExtra(WebViewActivity.EXTRA_WEB_TYPE, PayWebViewActivity.TYPE_URL)
                intent.putExtra(WebViewActivity.EXTRA_WEB_URL, result.redirect_url)
                startActivityForResult(intent, 2)
            }
            3 -> {

            }
        }
    }

    override fun onFailure(error: String) {
        hideLoadingDialog()
    }

    override fun paypalSuccess(msg: String) {
        toast(msg)
        startActivity(CompletePay::class.java)
    }

    override fun paypalFailure(msg: String) {
        toast(msg)
    }

    private val mPayDialog by lazy {
        AlertDialog.Builder(this)
                .setTitle(getString(R.string.transaction_is_not_set))
                .setMessage(getString(R.string.please_set_transaction_pin_to_continue))
                .setNegativeButton(getString(R.string.confirm_submit)) { dialog, which ->
                    val intent = Intent(mContext, SettingPayPwdActivity::class.java)
                    startActivity(intent)
                }
                .setNeutralButton(getString(R.string.tv_cancel)) { dialog, which ->

                }.create()
    }

    override fun onCheckPayPwdSuccess(data: CheckPayPwdEntity) {
        hideLoadingDialog()
        if ("0" == data.set) {//没有设置支付密码
            if(!mPayDialog.isShowing){
                mPayDialog.show()
            }
        } else {
            showPayPwdDialog()
        }
    }

    override fun onCheckPayPwdFailure(err: String) {
        hideLoadingDialog()
        toast(err)
    }

    override fun balanceSuccess(msg: String) {
        hideLoadingDialog()
        toast(msg)
        startActivity(CompletePay::class.java)
    }

    override fun balanceFailure(err: String) {
        hideLoadingDialog()
        toast(err)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data == null) {
            toast("支付取消")
        } else {
            when (requestCode) {
                1 -> {
                    if (resultCode == Activity.RESULT_OK) {
                        val result = data.getParcelableExtra<DropInResult>(DropInResult.EXTRA_DROP_IN_RESULT);
                        val mNonce = result.paymentMethodNonce?.nonce
                        if (mNonce != null) {
                            showLoadingDialog()
                            mPresenter.paypalBack(mOrderId, mNonce)
                        }
                    } else if (resultCode == Activity.RESULT_CANCELED) {
                        // the user canceled
                    } else {
                        // handle errors here, an exception may be available in
                        val error = data.getSerializableExtra(DropInActivity.EXTRA_ERROR) as Exception;
                        toast(error.message.toString())
                    }
                }

                2 -> {
                    if (resultCode == Activity.RESULT_OK) {
                        toast("支付成功")
                        startActivity(CompletePay::class.java)
                    } else if (resultCode == Activity.CONTEXT_RESTRICTED) {
                        toast("支付失败")
                    }
                }
            }
        }

    }


}
