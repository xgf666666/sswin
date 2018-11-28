package com.micropole.sxwine.module.personal.mvp.presenter

import com.example.mvpframe.BaseMvpPresenter
import com.micropole.sxwine.base.toast
import com.micropole.sxwine.module.personal.Bean.AddressManagerEntity
import com.micropole.sxwine.module.personal.mvp.contract.AddressManagerContract
import com.micropole.sxwine.module.personal.mvp.model.AddressManagerModel
import com.micropole.sxwine.utils.network.HttpObserver

/**
 * Created by JohnnyH on 2018/6/8.
 */
class AddressManagerPresenter : BaseMvpPresenter<AddressManagerContract.Model,AddressManagerContract.View>(),AddressManagerContract.Presenter {
    override fun deleteAddress(address_id: String?) {
        mModel.deleteAddress(address_id,object : HttpObserver<Any>(){
            override fun onSuccess(bean: Any, msg: String) {
                toast(msg)
                mView?.onDeleteAddressSuccess()
            }

            override fun onFailure(code: String, msg: String) {
                mView?.onDeleteAddressFailure(msg)
            }

        })
    }

    override fun createModel(): AddressManagerContract.Model = AddressManagerModel()

    override fun loadData(isRefresh: Boolean, page: String?, per_page: String?) {
        mModel.loadData(page,per_page,object : HttpObserver<List<AddressManagerEntity>>(){
            override fun onSuccess(bean: List<AddressManagerEntity>, msg: String) {
                if (isRefresh){
                    mView?.setData(bean)
                }else{
                    mView?.addData(bean)
                }
            }

            override fun onFailure(code: String, msg: String) {
                mView?.onDataFailure(isRefresh,msg)
            }
        })
    }
}