package com.micropole.sxwine.module.order.mvp.presenter

import com.example.mvpframe.BaseMvpPresenter
import com.micropole.sxwine.bean.ConfirmResult
import com.micropole.sxwine.bean.SettleResult
import com.micropole.sxwine.module.order.mvp.contract.ConfirmContract
import com.micropole.sxwine.module.order.mvp.model.ConfirmModel
import com.micropole.sxwine.utils.network.HttpObserver

/**
 * Description:
 * Created by DarkHorse on 2018/6/5.
 */
class ConfirmPresenter : ConfirmContract.Presenter, BaseMvpPresenter<ConfirmContract.Model, ConfirmContract.View>() {

    override fun updateOrder(temp_id: String, address_id: String?, self_pick: Int, self_pick_address_id: Int?, receiver: String?, mobile: String? ) {
        mModel.updateOrder(temp_id, address_id, self_pick, self_pick_address_id, receiver, mobile, object : HttpObserver<SettleResult>() {
            override fun onSuccess(bean: SettleResult, msg: String) {
                mView?.onUpdateSuccess(bean, msg)
            }

            override fun onFailure(code: String, msg: String) {
                mView?.onUpdateFailure(msg)
            }
        })
    }

    override fun submitOrder(temp_id: String) {
        mModel.submitOrder(temp_id, object : HttpObserver<ConfirmResult>() {
            override fun onSuccess(bean: ConfirmResult, msg: String) {
                mView?.onSuccess(bean, msg)
            }

            override fun onFailure(code: String, msg: String) {
                mView?.onFailure(msg)
            }
        })
    }

    override fun createModel(): ConfirmContract.Model = ConfirmModel()

}
