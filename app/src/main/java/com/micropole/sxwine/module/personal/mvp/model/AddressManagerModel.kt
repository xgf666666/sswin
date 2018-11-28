package com.micropole.sxwine.module.personal.mvp.model

import com.darkhorse.httphelper.transformer.RxTransformer
import com.micropole.sxwine.module.personal.Bean.AddressManagerEntity
import com.micropole.sxwine.module.personal.mvp.contract.AddressManagerContract
import com.micropole.sxwine.utils.network.API
import com.micropole.sxwine.utils.network.HttpObserver

/**
 * Created by JohnnyH on 2018/6/8.
 */
class AddressManagerModel : AddressManagerContract.Model {
    override fun deleteAddress(address_id: String?, httpObserver: HttpObserver<Any>?) {
        API.service.deleteAddress(address_id)
                .compose(RxTransformer.io_main())
                .subscribe(httpObserver!!)
    }

    override fun loadData(page: String?, per_page: String?, httpObserver: HttpObserver<List<AddressManagerEntity>>?) {
        API.service.getAddressList(page,per_page)
                .compose(RxTransformer.io_main())
                .subscribe(httpObserver!!)
    }
}