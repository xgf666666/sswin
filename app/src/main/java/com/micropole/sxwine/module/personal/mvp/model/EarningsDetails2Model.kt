package com.micropole.sxwine.module.personal.mvp.model

import com.darkhorse.httphelper.transformer.RxTransformer
import com.micropole.sxwine.module.personal.Bean.EarningsDetails2Entity
import com.micropole.sxwine.module.personal.mvp.contract.EarningsDetails2Contract
import com.micropole.sxwine.utils.network.API
import com.micropole.sxwine.utils.network.HttpObserver

/**
 * Created by JohnnyH on 2018/6/13.
 */
class EarningsDetails2Model : EarningsDetails2Contract.Model {
    override fun loadData(type: String?, page: String?, per_page: String?, httpObserver: HttpObserver<List<EarningsDetails2Entity>>?) {
        API.service.getEarningsDetails2Data(type,page,per_page)
                .compose(RxTransformer.io_main())
                .subscribe(httpObserver!!)
    }
}