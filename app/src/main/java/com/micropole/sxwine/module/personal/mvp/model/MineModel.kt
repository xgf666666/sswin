package com.micropole.sxwine.module.personal.mvp.model

import com.darkhorse.httphelper.transformer.RxTransformer
import com.micropole.sxwine.module.personal.Bean.OrderStatusEntity
import com.micropole.sxwine.module.personal.Bean.UserInfoEntity
import com.micropole.sxwine.module.personal.mvp.contract.MineContract
import com.micropole.sxwine.utils.network.API
import com.micropole.sxwine.utils.network.HttpObserver

/**
 * Description:
 * Created by DarkHorse on 2018/5/28.
 */
class MineModel : MineContract.Model{
    override fun getOrderStatus(httpObserver: HttpObserver<OrderStatusEntity>) {
        API.service.getOrderStatus()
                .compose(RxTransformer.io_main())
                .subscribe(httpObserver)
    }

    override fun getUserInfo(httpObserver: HttpObserver<UserInfoEntity>) {
        API.service.getUserInfo()
                .compose(RxTransformer.io_main())
                .subscribe(httpObserver)

    }

}
