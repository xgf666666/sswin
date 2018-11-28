package com.micropole.sxwine.module.login.mvp.model

import com.darkhorse.httphelper.transformer.RxTransformer
import com.micropole.sxwine.module.login.bean.ADEntity
import com.micropole.sxwine.module.login.mvp.contract.SplashContract
import com.micropole.sxwine.utils.network.API
import com.micropole.sxwine.utils.network.HttpObserver

/**
 * Created by JohnnyH on 2018/8/28.
 */
class SplashModel : SplashContract.Model {
    override fun loadData(httpObserver: HttpObserver<ADEntity>?) {
        API.service.getAd().compose(RxTransformer.io_main()).subscribe(httpObserver!!)
    }
}