package com.micropole.sxwine.module.personal.mvp.model

import com.darkhorse.httphelper.transformer.RxTransformer
import com.micropole.sxwine.module.personal.Bean.AddAddressEntity
import com.micropole.sxwine.module.personal.mvp.contract.AddAddressContract
import com.micropole.sxwine.utils.network.API
import com.micropole.sxwine.utils.network.HttpObserver

/**
 * Created by JohnnyH on 2018/6/11.
 */
class AddAddressModel : AddAddressContract.Model {

    override fun compileAddress(mobile: String?, receiver: String?, address_detail: String?, is_default: String?, address_id: String?, httpObserver: HttpObserver<AddAddressEntity>?) {
        API.service.compileAddress(mobile,receiver,address_detail,is_default,address_id)
                .compose(RxTransformer.io_main())
                .subscribe(httpObserver!!)


    }

    override fun addAddress(mobile: String?, receiver: String?, address_detail: String?, is_default: String?, httpObserver: HttpObserver<AddAddressEntity>?) {
        API.service.addAddress(mobile,receiver,address_detail,is_default)
                .compose(RxTransformer.io_main())
                .subscribe(httpObserver!!)
    }
}