package com.micropole.sxwine.module.personal.mvp.model

import com.darkhorse.httphelper.transformer.RxTransformer
import com.micropole.sxwine.module.personal.Bean.BankRecordEntity
import com.micropole.sxwine.module.personal.Bean.CheckPayPwdEntity
import com.micropole.sxwine.module.personal.Bean.EarningsWithdrawEntity
import com.micropole.sxwine.module.personal.mvp.contract.EarningsWithdrawContract
import com.micropole.sxwine.utils.network.API
import com.micropole.sxwine.utils.network.HttpObserver

/**
 * Created by JohnnyH on 2018/6/14.
 */
class EarningsWithdrawModel : EarningsWithdrawContract.Model {
    override fun getBankRecord(httpObserver: HttpObserver<BankRecordEntity>?) {
        API.service.getBankRecord()
                .compose(RxTransformer.io_main())
                .subscribe(httpObserver!!)
    }

    override fun earningsWithdraw2(account: String?, card_holder: String?, withdraw_amount: String?,
                                   deal_password: String?, bank_name: String?,swift_code: String?,
                                   bank_address: String?,httpObserver: HttpObserver<EarningsWithdrawEntity>?) {
        API.service.earningsWithdraw2(account,card_holder,withdraw_amount,deal_password,bank_name,swift_code,bank_address)
                .compose(RxTransformer.io_main())
                .subscribe(httpObserver!!)
    }

    override fun checkPayPwd(httpObserver: HttpObserver<CheckPayPwdEntity>?) {
        API.service.checkPayPwd()
                .compose(RxTransformer.io_main())
                .subscribe(httpObserver!!)
    }

    override fun earningsWithdraw(account: String?, type: String?, withdraw_amount: String?, deal_password: String?, httpObserver: HttpObserver<EarningsWithdrawEntity>?) {
        API.service.earningsWithdraw(account,type,withdraw_amount,deal_password)
                .compose(RxTransformer.io_main())
                .subscribe(httpObserver!!)
    }

}