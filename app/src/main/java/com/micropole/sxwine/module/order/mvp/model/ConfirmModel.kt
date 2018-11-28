package com.micropole.sxwine.module.order.mvp.model

import com.darkhorse.httphelper.transformer.RxTransformer
import com.micropole.sxwine.bean.BaseResponseBean
import com.micropole.sxwine.bean.ConfirmOrderBean
import com.micropole.sxwine.bean.ConfirmResult
import com.micropole.sxwine.bean.SettleResult
import com.micropole.sxwine.module.order.mvp.contract.ConfirmContract
import com.micropole.sxwine.utils.network.API
import com.micropole.sxwine.utils.network.HttpObserver

/**
 * Description:
 * Created by DarkHorse on 2018/6/5.
 */
class ConfirmModel : ConfirmContract.Model {
    override fun updateOrder(temp_id: String, address_id: String?, self_pick: Int, self_pick_address_id: Int?, receiver: String?, mobile: String?, httpObserver: HttpObserver<SettleResult>) {
        API.service.updateOrder(temp_id, address_id, self_pick, self_pick_address_id, receiver, mobile)
                .compose(RxTransformer.io_main())
                .subscribe(httpObserver)
    }

    override fun submitOrder(temp_id: String, httpObserver: HttpObserver<ConfirmResult>) {
        API.service.submitOrder(temp_id)
                .compose(RxTransformer.io_main())
                .subscribe(httpObserver)
    }

}
