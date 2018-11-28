package com.micropole.sxwine.module.personal

import android.content.Intent
import android.text.*
import android.view.View
import com.example.mvpframe.BaseMvpActivity
import com.micropole.sxwine.R
import com.micropole.sxwine.base.hideLoadingDialog
import com.micropole.sxwine.base.initToolBar
import com.micropole.sxwine.base.showLoadingDialog
import com.micropole.sxwine.base.toast
import com.micropole.sxwine.module.personal.Bean.BankRecordEntity
import com.micropole.sxwine.module.personal.Bean.CheckPayPwdEntity
import com.micropole.sxwine.module.personal.Bean.EarningsWithdrawEntity
import com.micropole.sxwine.module.personal.mvp.contract.EarningsWithdrawContract
import com.micropole.sxwine.module.personal.mvp.presenter.EarningsWithdrawPresenter
import com.micropole.sxwine.widgets.InputPwdDialog
import kotlinx.android.synthetic.main.activity_earnings_withcdraw2.*
import kotlinx.android.synthetic.main.item_toolbar.*
import java.util.regex.Pattern

/**
 * Created by JohnnyH on 2018/6/14.
 * 收益提现
 */
class EarningsWithdrawActivity2 : BaseMvpActivity<EarningsWithdrawContract.Model, EarningsWithdrawContract.View, EarningsWithdrawPresenter>(), EarningsWithdrawContract.View, View.OnClickListener {

    private lateinit var mTotal_price : String

    override fun createPresenter(): EarningsWithdrawPresenter = EarningsWithdrawPresenter()
    override fun getLayoutId(): Int = R.layout.activity_earnings_withcdraw2

    override fun initView() {
        mTotal_price=intent.getStringExtra("total_price")
        initToolBar(getString(R.string.earnings_withdraw))
        toolbar.setNavigationOnClickListener { finish() }
        btn_confirm_withdraw.setOnClickListener(this)
        tv_all_withdraw.setOnClickListener(this)
        tv_total_price.text=getString(R.string.can_withdraw_money)+" "+mTotal_price
    }

    override fun initData() {
        mPresenter.getBankRecord()
    }

    override fun initListener() {
        ed_money.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                var s = s
                //删除.后面超过两位的数字
                if (s.toString().contains(".")) {
                    if (s.length - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 3)
                        ed_money.setText(s)
                        ed_money.setSelection(s.length)
                    }
                }
                //如果.在起始位置,则起始位置自动补0
                if (s.toString().trim { it <= ' ' }.substring(0) == ".") {
                    s = "0" + s
                    ed_money.setText(s)
                    ed_money.setSelection(2)
                }
                //如果起始位置为0并且第二位跟的不是".",则无法后续输入
                if (s.toString().startsWith("0") && s.toString().trim { it <= ' ' }.length > 1) {
                    if (s.toString().substring(1, 2) != ".") {
                        ed_money.setText(s.subSequence(0, 1))
                        ed_money.setSelection(1)
                        return
                    }
                }
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }
            override fun afterTextChanged(s: Editable) {
            }

        })

        val typeFilter = object : InputFilter {
            override fun filter(source: CharSequence, start: Int, end: Int, dest: Spanned, dstart: Int, dend: Int): CharSequence? {
                val p = Pattern.compile("[a-zA-Z|\u4e00-\u9fa5]+")
                val m = p.matcher(source.toString())
                return if (!m.matches()) "" else null
            }
        }

        ed_card_holder.filters= arrayOf(typeFilter)
    }

    override fun onClick(view: View?) {
        when(view!!.id){
            R.id.tv_all_withdraw ->{//全部提现
                ed_money.setText(mTotal_price)
            }
            R.id.btn_confirm_withdraw ->{//确认提现
                val money = ed_money.text.toString().trim()
                val bank_name = ed_bank_name.text.toString().trim()
                val card_hoder = ed_card_holder.text.toString().trim()
                val account = ed_account.text.toString().trim()
                val swift_code = ed_swift_code.text.toString().trim()
                val bank_address = ed_bank_address.text.toString().trim()
                if (TextUtils.isEmpty(money)){
                    toast(getString(R.string.input_withdraw_price))
                    return
                }else if (TextUtils.isEmpty(bank_name)){
                    toast(getString(R.string.Please_key_in_Bank_Name))
                    return
                }else if (TextUtils.isEmpty(card_hoder)){
                    toast(getString(R.string.Please_key_in_Account_Name))
                    return
                }else if (TextUtils.isEmpty(account)){
                    toast(getString(R.string.Please_key_in_Account_No))
                    return
                }else if (TextUtils.isEmpty(swift_code)){
                    toast(getString(R.string.input_swift))
                    return
                }else if (TextUtils.isEmpty(bank_address)){
                    toast(getString(R.string.input_bank_address))
                    return
                }
                showLoadingDialog()
                mPresenter.checkPayPwd()
            }
        }
    }


    override fun onCheckPayPwdSuccess(data: CheckPayPwdEntity?) {
        hideLoadingDialog()
        if ("0"==data!!.set){//没有设置支付密码
            val intent = Intent(mContext, SettingPayPwdActivity::class.java)
            startActivity(intent)
        }else{
            showPayPwdDialog()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==0){
            finish()
        }
    }

    private fun showPayPwdDialog() {
        val inputPwdDialog = InputPwdDialog(mContext)
        inputPwdDialog.setOnPswDialogClickListener(object : InputPwdDialog.OnPswDialogClickListener {
            override fun onConfirm(password: String?) {

                val money = ed_money.text.toString().trim()
                val bank_name = ed_bank_name.text.toString().trim()
                val card_hoder = ed_card_holder.text.toString().trim()
                val account = ed_account.text.toString().trim()
                val swift_code = ed_swift_code.text.toString().trim()
                val bank_address = ed_bank_address.text.toString().trim()
                if (TextUtils.isEmpty(money)){
                    toast(getString(R.string.input_withdraw_price))
                    inputPwdDialog.dismiss()
                    return
                }else if (TextUtils.isEmpty(bank_name)){
                    toast(getString(R.string.Please_key_in_Bank_Name))
                    inputPwdDialog.dismiss()
                    return
                }else if (TextUtils.isEmpty(card_hoder)){
                    toast(getString(R.string.Please_key_in_Account_Name))
                    inputPwdDialog.dismiss()
                    return
                }else if (TextUtils.isEmpty(account)){
                    toast(getString(R.string.Please_key_in_Account_No))
                    inputPwdDialog.dismiss()
                    return
                }
                showLoadingDialog()
                mPresenter.earningsWithdraw2(account,card_hoder,money,password,bank_name,swift_code,bank_address)
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

    override fun onCheckPayPwdFailure(err: String?) {
        hideLoadingDialog()
        toast(err!!)
    }

    override fun onEarningsWithdrawSuccess(data: EarningsWithdrawEntity?) {
        hideLoadingDialog()
        val intent = Intent(mContext, WithdrawCompleteActivity::class.java)
        startActivityForResult(intent,0)
    }

    override fun onEarningsWithdrawFailure(err: String?) {
        hideLoadingDialog()
        toast(err!!)
    }

    override fun onGetBankRecordSuccess(data: BankRecordEntity?) {
        if(!TextUtils.isEmpty(data!!.bank_name)){
            ed_bank_name.setText(data.bank_name)
            ed_card_holder.setText(data.card_holder)
            ed_account.setText(data.account)
            ed_swift_code.setText(data.swift_code)
            ed_bank_address.setText(data.bank_address)
            ed_bank_name.isClickable=false
            ed_bank_name.isFocusable=false
            ed_card_holder.isClickable=false
            ed_card_holder.isFocusable=false
            ed_account.isClickable=false
            ed_account.isFocusable=false
            ed_swift_code.isClickable=false
            ed_swift_code.isFocusable=false
            ed_bank_address.isClickable=false
            ed_bank_address.isFocusable=false
        }
    }

}