package com.micropole.sxwine.module.personal.mvp.model

import com.darkhorse.httphelper.transformer.RxTransformer
import com.micropole.sxwine.module.personal.Bean.FQAEntity
import com.micropole.sxwine.module.personal.mvp.contract.FQAContract
import com.micropole.sxwine.utils.network.API
import com.micropole.sxwine.utils.network.HttpObserver

/**
 * Created by JohnnyH on 2018/6/12.
 */
class FQAModel : FQAContract.Model {
    override fun loadData(httpObserver: HttpObserver<List<FQAEntity>>?) {
        API.service.getFQAList()
                .compose(RxTransformer.io_main())
                .subscribe(httpObserver!!)
    }
}