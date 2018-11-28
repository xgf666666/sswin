package com.micropole.sxwine.module.personal.mvp.model

import com.darkhorse.httphelper.transformer.RxTransformer
import com.micropole.sxwine.module.personal.Bean.ContactServiceEntity
import com.micropole.sxwine.module.personal.mvp.contract.ContactServiceContract
import com.micropole.sxwine.utils.network.API
import com.micropole.sxwine.utils.network.HttpObserver

/**
 * Created by JohnnyH on 2018/6/12.
 */
class ContactServiceModel : ContactServiceContract.Model {
    override fun serviceOnline(httpObserver: HttpObserver<ContactServiceEntity>?) {
        API.service.serviceOnline()
                .compose(RxTransformer.io_main())
                .subscribe(httpObserver!!)
    }

    override fun consumeService(httpObserver: HttpObserver<ContactServiceEntity>?) {
        API.service.consumeService()
                .compose(RxTransformer.io_main())
                .subscribe(httpObserver!!)
    }
}