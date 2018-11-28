package com.micropole.sxwine.module.personal.mvp.model

import com.darkhorse.httphelper.transformer.RxTransformer
import com.micropole.sxwine.module.personal.Bean.WithdrawListEntity
import com.micropole.sxwine.module.personal.mvp.contract.WithdrawListContract
import com.micropole.sxwine.utils.network.API
import com.micropole.sxwine.utils.network.HttpObserver

/**
 * Created by JohnnyH on 2018/6/14.
 */
class WithdrawListModel : WithdrawListContract.Model {
    override fun loadData(page: String?, httpObserver: HttpObserver<List<WithdrawListEntity>>?) {
        API.service.getWithDrawListData(page)
                .compose(RxTransformer.io_main())
                .subscribe(httpObserver!!)
    }
}