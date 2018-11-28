package com.micropole.sxwine.module.order.mvp.contract

import com.example.mvpframe.BaseMvpView
import com.micropole.sxwine.bean.BaseResponseBean
import com.micropole.sxwine.bean.ConfirmOrderBean
import com.micropole.sxwine.bean.ConfirmResult
import com.micropole.sxwine.bean.SettleResult
import com.micropole.sxwine.utils.network.HttpObserver

/**
 * Description:
 * Created by DarkHorse on 2018/6/5.
 */
interface ConfirmContract {
    interface Model {
        fun submitOrder(temp_id: String, httpObserver: HttpObserver<ConfirmResult>)

        fun updateOrder(temp_id: String, address_id: String?, self_pick: Int, self_pick_address_id: Int?, mobile: String?, receiver: String?, httpObserver: HttpObserver<SettleResult>)
    }

    interface View : BaseMvpView {
        fun onSuccess(bean: ConfirmResult, msg: String)

        fun onFailure(err: String)

        fun onUpdateSuccess(bean: SettleResult, msg: String)

        fun onUpdateFailure(err: String)
    }

    interface Presenter {
        fun submitOrder(temp_id: String)

        fun updateOrder(temp_id: String, address_id: String?, self_pick: Int, self_pick_address_id: Int? = null, mobile: String? = null, receiver: String? = null)
    }
}
