package com.micropole.sxwine.module.personal.mvp.presenter

import com.example.mvpframe.BaseMvpPresenter
import com.micropole.sxwine.base.toast
import com.micropole.sxwine.module.personal.Bean.OrderDetailsEntity
import com.micropole.sxwine.module.personal.mvp.contract.OrderDetailsContract
import com.micropole.sxwine.module.personal.mvp.model.OrderDetailsModel
import com.micropole.sxwine.utils.network.HttpObserver

/**
 * Created by JohnnyH on 2018/6/19.
 */
class OrderDetailsPresenter : BaseMvpPresenter<OrderDetailsContract.Model,OrderDetailsContract.View>(),OrderDetailsContract.Presenter {

    override fun confirmReceive(order_id: String?) {
        mModel.confirmReceive(order_id,object : HttpObserver<Any>(){
            override fun onSuccess(bean: Any, msg: String) {
                toast(msg)
                mView?.onConfirmReceiveSuccess()
            }

            override fun onFailure(code: String, msg: String) {
                mView?.onConfirmReceiveFailure(msg)
            }

        })
    }

    override fun cancelOrder(order_id: String?) {
        mModel.cancelOrder(order_id,object : HttpObserver<Any>(){
            override fun onSuccess(bean: Any, msg: String) {
                toast(msg)
                mView?.onCancelOrderSuccess()
            }

            override fun onFailure(code: String, msg: String) {
                mView?.onCancelOrderFailure(msg)
            }

        })
    }

    override fun deleteOrder(order_id: String?) {
        mModel.deleteOrder(order_id,object : HttpObserver<Any>(){
            override fun onSuccess(bean: Any, msg: String) {
                toast(msg)
                mView?.onDeleteOrderSuccess()
            }

            override fun onFailure(code: String, msg: String) {
                mView?.onCancelOrderFailure(msg)
            }

        })
    }

    override fun loadData(isFirstLoading : Boolean,order_id: String?) {
        mModel.loadData(order_id,object : HttpObserver<OrderDetailsEntity>(){

            override fun onSuccess(bean: OrderDetailsEntity, msg: String) {
                mView?.setData(bean)
            }

            override fun onFailure(code: String, msg: String) {
                mView?.onDataFailure(msg,isFirstLoading)
            }
        })
    }

    override fun createModel(): OrderDetailsContract.Model = OrderDetailsModel()
}