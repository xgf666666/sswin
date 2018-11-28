package com.micropole.sxwine.module.personal

import android.content.Intent
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
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
import com.micropole.sxwine.widgets.WithdrawDialog
import kotlinx.android.synthetic.main.activity_earnings_withcdraw.*
import kotlinx.android.synthetic.main.item_toolbar.*


/**
 * Created by JohnnyH on 2018/6/14.
 * 收益提现
 */
class EarningsWithdrawActivity  : BaseMvpActivity<EarningsWithdrawContract.Model,EarningsWithdrawContract.View,EarningsWithdrawPresenter>(),EarningsWithdrawContract.View, View.OnClickListener {
    override fun onGetBankRecordSuccess(data: BankRecordEntity?) {

    }

    private lateinit var mTotal_price : String

    override fun createPresenter(): EarningsWithdrawPresenter = EarningsWithdrawPresenter()
    override fun getLayoutId(): Int = R.layout.activity_earnings_withcdraw

    override fun initView() {
        mTotal_price=intent.getStringExtra("total_price")
        initToolBar(getString(R.string.earnings_withdraw))
        toolbar.setNavigationOnClickListener { finish() }
        ll_pathway.setOnClickListener(this)
        btn_confirm_withdraw.setOnClickListener(this)
        tv_all_withdraw.setOnClickListener(this)
        tv_total_price.text=getString(R.string.can_withdraw_money)+" "+mTotal_price
    }

    override fun initData() {

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
    }

    override fun onClick(view: View?) {
        when(view!!.id){
            R.id.ll_pathway->{//转账途径
                showSelectPayModelDialog()
            }
            R.id.tv_all_withdraw->{//全部提现
                ed_money.setText(mTotal_price)
            }
            R.id.btn_confirm_withdraw->{//确认提现
                showLoadingDialog()
                mPresenter.checkPayPwd()
            }
        }
    }

    private var mPayModel : String =""
    private fun showSelectPayModelDialog() {
        val withdrawDialog = WithdrawDialog(mContext)
        withdrawDialog.setPayModel(mPayModel)
        withdrawDialog.setOnSelectPayModelListener(object : WithdrawDialog.OnSelectPayModelListener{
            override fun selectPayModel(type: String?) {
                mPayModel=type!!
                tv_pay_model.text=mPayModel
            }

        })
        withdrawDialog.show()
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
        inputPwdDialog.setOnPswDialogClickListener(object : InputPwdDialog.OnPswDialogClickListener{
            override fun onConfirm(password: String?) {
                val account = ed_account.text.toString().trim()
                val money = ed_money.text.toString().trim()
                if (TextUtils.isEmpty(mPayModel)){
                    toast(getString(R.string.select_pathway))
                    return
                }else if (TextUtils.isEmpty(account)){
                    toast(getString(R.string.input_account))
                    return
                }else if (TextUtils.isEmpty(money)){
                    toast(getString(R.string.input_withdraw_price))
                    return
                }
                showLoadingDialog()
                mPresenter.earningsWithdraw(account,mPayModel,money,password)
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

}