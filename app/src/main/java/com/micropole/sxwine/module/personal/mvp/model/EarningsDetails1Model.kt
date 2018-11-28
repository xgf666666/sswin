package com.micropole.sxwine.module.personal.mvp.model

import com.darkhorse.httphelper.transformer.RxTransformer
import com.micropole.sxwine.module.personal.Bean.AccumulateEarningsEntity
import com.micropole.sxwine.module.personal.Bean.EarningsDetails1Entity
import com.micropole.sxwine.module.personal.mvp.contract.EarningDetails1Contract
import com.micropole.sxwine.utils.network.API
import com.micropole.sxwine.utils.network.HttpObserver

/**
 * Created by JohnnyH on 2018/6/13.
 */
class EarningsDetails1Model : EarningDetails1Contract.Model {
    override fun getAccumulateEarnings(httpObserver: HttpObserver<AccumulateEarningsEntity>?) {
        API.service.getAccumulateEarnings()
                .compose(RxTransformer.io_main())
                .subscribe(httpObserver!!)
    }

    override fun loadData(type: String?, httpObserver: HttpObserver<EarningsDetails1Entity>?) {
        API.service.getEarningsDetails1Data(type)
                .compose(RxTransformer.io_main())
                .subscribe(httpObserver!!)
    }
}