package com.micropole.sxwine.module.personal.mvp.presenter

import com.blankj.utilcode.util.EncryptUtils
import com.example.mvpframe.BaseMvpPresenter
import com.micropole.sxwine.base.toast
import com.micropole.sxwine.module.personal.Bean.BankRecordEntity
import com.micropole.sxwine.module.personal.Bean.CheckPayPwdEntity
import com.micropole.sxwine.module.personal.Bean.EarningsWithdrawEntity
import com.micropole.sxwine.module.personal.mvp.contract.EarningsWithdrawContract
import com.micropole.sxwine.module.personal.mvp.model.EarningsWithdrawModel
import com.micropole.sxwine.utils.network.HttpObserver

/**
 * Created by JohnnyH on 2018/6/14.
 */
class EarningsWithdrawPresenter : BaseMvpPresenter<EarningsWithdrawContract.Model,EarningsWithdrawContract.View>(),EarningsWithdrawContract.Presenter {
    override fun getBankRecord() {
        mModel?.getBankRecord(object : HttpObserver<BankRecordEntity>(){
            override fun onSuccess(bean: BankRecordEntity, msg: String) {
                mView?.onGetBankRecordSuccess(bean)
            }

            override fun onFailure(code: String, msg: String) {
                toast(msg)
            }

        })
    }

    override fun earningsWithdraw2(account: String?, card_holder: String?, withdraw_amount: String?, deal_password: String?, bank_name: String?,swift_code: String?,bank_address: String?) {
        mModel?.earningsWithdraw2(account,card_holder,withdraw_amount,EncryptUtils.encryptMD5ToString(deal_password).toLowerCase(),bank_name,
                swift_code,bank_address,object : HttpObserver<EarningsWithdrawEntity>(){
                    override fun onSuccess(bean: EarningsWithdrawEntity, msg: String) {
                        toast(msg)
                        mView?.onEarningsWithdrawSuccess(bean)
                    }

                    override fun onFailure(code: String, msg: String) {
                        mView?.onEarningsWithdrawFailure(msg)
                    }

                })
    }

    override fun checkPayPwd() {
        mModel?.checkPayPwd(object : HttpObserver<CheckPayPwdEntity>(){
            override fun onSuccess(bean: CheckPayPwdEntity, msg: String) {
                mView?.onCheckPayPwdSuccess(bean)
            }

            override fun onFailure(code: String, msg: String) {
                mView?.onCheckPayPwdFailure(msg)
            }

        })
    }

    override fun earningsWithdraw(account: String?, type: String?, withdraw_amount: String?, deal_password: String?) {

        mModel?.earningsWithdraw(account,type,withdraw_amount, EncryptUtils.encryptMD5ToString(deal_password).toLowerCase(),
                object : HttpObserver<EarningsWithdrawEntity>(){

            override fun onSuccess(bean: EarningsWithdrawEntity, msg: String) {
                toast(msg)
                mView?.onEarningsWithdrawSuccess(bean)
            }

            override fun onFailure(code: String, msg: String) {
                mView?.onEarningsWithdrawFailure(msg)
            }

        })
    }

    override fun createModel(): EarningsWithdrawContract.Model = EarningsWithdrawModel()
}