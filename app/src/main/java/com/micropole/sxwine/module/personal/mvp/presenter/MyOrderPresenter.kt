package com.micropole.sxwine.module.personal.mvp.presenter

import com.example.mvpframe.BaseMvpPresenter
import com.micropole.sxwine.base.toast
import com.micropole.sxwine.module.personal.Bean.MyOrderItemEntity
import com.micropole.sxwine.module.personal.mvp.contract.MyOrderContract
import com.micropole.sxwine.module.personal.mvp.model.MyOrderModel
import com.micropole.sxwine.utils.network.HttpObserver

/**
 * Created by JohnnyH on 2018/6/15.
 */
class MyOrderPresenter : BaseMvpPresenter<MyOrderContract.Model,MyOrderContract.View>(),MyOrderContract.Presenter {

    override fun deleteOrder(order_id: String?) {
        mModel.deleteOrder(order_id,object : HttpObserver<Any>(){
            override fun onSuccess(bean: Any, msg: String) {
                toast(msg)
                mView?.onDeleteOrderSuccess()
            }

            override fun onFailure(code: String, msg: String) {
                mView?.onDeleteOrderFailure(msg)
            }

        })
    }

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
                mView?.onConfirmReceiveFailure(msg)
            }

        })
    }

    override fun loadData(isRefresh: Boolean, isFirstLoading: Boolean, page: String?, per_page: String?, order_status: String?) {
        mModel.loadData(page,per_page,order_status,object : HttpObserver<List<MyOrderItemEntity>>(){
            override fun onSuccess(bean: List<MyOrderItemEntity>, msg: String) {
                if (isRefresh){
                    mView?.setData(bean)
                }else{
                    mView?.addData(bean)
                }
            }

            override fun onFailure(code: String, msg: String) {
                mView?.onDataFailure(msg,isFirstLoading)
            }

        })
    }

    override fun createModel(): MyOrderContract.Model = MyOrderModel()
}