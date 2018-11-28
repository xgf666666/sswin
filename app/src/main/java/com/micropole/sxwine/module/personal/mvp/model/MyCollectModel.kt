package com.micropole.sxwine.module.personal.mvp.model

import com.darkhorse.httphelper.transformer.RxTransformer
import com.micropole.sxwine.module.personal.Bean.MyCollectEntity
import com.micropole.sxwine.module.personal.mvp.contract.MyCollectContract
import com.micropole.sxwine.utils.network.API
import com.micropole.sxwine.utils.network.HttpObserver

/**
 * Created by JohnnyH on 2018/6/12.
 */
class MyCollectModel : MyCollectContract.Model {
    override fun loadData(page: String?, per_page: String?, httpObserver: HttpObserver<List<MyCollectEntity>>?) {
        API.service.getMyCollectList(page,per_page)
                .compose(RxTransformer.io_main())
                .subscribe(httpObserver!!)
    }
}