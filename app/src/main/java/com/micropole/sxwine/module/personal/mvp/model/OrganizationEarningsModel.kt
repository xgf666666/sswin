package com.micropole.sxwine.module.personal.mvp.model

import com.darkhorse.httphelper.transformer.RxTransformer
import com.micropole.sxwine.module.personal.Bean.OrganizationEarningsEntity
import com.micropole.sxwine.module.personal.mvp.contract.OrganizationEarningsContract
import com.micropole.sxwine.utils.network.API
import com.micropole.sxwine.utils.network.HttpObserver

/**
 * Created by JohnnyH on 2018/6/14.
 */
class OrganizationEarningsModel : OrganizationEarningsContract.Model {

    override fun loadData(page: String?, per_page: String?,type: String?, httpObserver: HttpObserver<List<OrganizationEarningsEntity>>?) {
       if ("1"==type){
           API.service.getDirectlyDetail(page,per_page)
                   .compose(RxTransformer.io_main())
                   .subscribe(httpObserver!!)
       }else if ("2"==type){
           API.service.getOrganizationDetail(page,per_page)
                   .compose(RxTransformer.io_main())
                   .subscribe(httpObserver!!)
       }else if ("3"==type){
           API.service.getConsumeDetail(page,per_page)
                   .compose(RxTransformer.io_main())
                   .subscribe(httpObserver!!)
       }
    }
}