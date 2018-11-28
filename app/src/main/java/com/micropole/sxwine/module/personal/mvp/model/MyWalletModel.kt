package com.micropole.sxwine.module.personal.mvp.model

import com.darkhorse.httphelper.transformer.RxTransformer
import com.micropole.sxwine.module.personal.Bean.MyWalletEntity
import com.micropole.sxwine.module.personal.mvp.contract.MyWalletContract
import com.micropole.sxwine.utils.network.API
import com.micropole.sxwine.utils.network.HttpObserver

/**
 * Created by JohnnyH on 2018/6/13.
 */
class MyWalletModel : MyWalletContract.Model {

    override fun loadData(httpObserver: HttpObserver<MyWalletEntity>?) {
        API.service.getMyWallet()
                .compose(RxTransformer.io_main())
                .subscribe(httpObserver!!)
    }
}