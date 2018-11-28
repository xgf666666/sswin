package com.micropole.sxwine.module.home.mvp.model

import com.darkhorse.httphelper.transformer.RxTransformer
import com.micropole.sxwine.bean.ConfirmAddressResult
import com.micropole.sxwine.module.home.mvp.contract.ConfirmAddressContract
import com.micropole.sxwine.utils.network.API
import com.micropole.sxwine.utils.network.HttpObserver

/**
 * Description:
 * Created by DarkHorse on 2018/9/14.
 */
class ConfirmAddressModel : ConfirmAddressContract.Model {
    override fun getAddress(httpObserver: HttpObserver<ConfirmAddressResult>) {
        API.service.getConfirmAddressList()
                .compose(RxTransformer.io_main())
                .subscribe(httpObserver)
    }
}
