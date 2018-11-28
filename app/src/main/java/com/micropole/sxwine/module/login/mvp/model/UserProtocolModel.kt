package com.micropole.sxwine.module.login.mvp.model

import com.darkhorse.httphelper.transformer.RxTransformer
import com.micropole.sxwine.module.login.bean.UserProtocolEntity
import com.micropole.sxwine.module.login.mvp.contract.UserProtocolContract
import com.micropole.sxwine.utils.network.API
import com.micropole.sxwine.utils.network.HttpObserver

/**
 * Created by JohnnyH on 2018/9/17.
 */
class UserProtocolModel : UserProtocolContract.Model {
    override fun getProtocol(httpObserver: HttpObserver<UserProtocolEntity>?) {
        API.service.getUserProtocol().compose(RxTransformer.io_main()).subscribe(httpObserver!!)
    }
}