package com.micropole.sxwine.module.home.mvp.presenter

import com.example.mvpframe.BaseMvpPresenter
import com.micropole.sxwine.base.i
import com.micropole.sxwine.bean.ConfirmAddressResult
import com.micropole.sxwine.module.home.mvp.contract.ConfirmAddressContract
import com.micropole.sxwine.module.home.mvp.model.ConfirmAddressModel
import com.micropole.sxwine.utils.network.HttpObserver

/**
 * Description:
 * Created by DarkHorse on 2018/9/14.
 */
class ConfirmAddressPresenter : ConfirmAddressContract.Presenter, BaseMvpPresenter<ConfirmAddressContract.Model, ConfirmAddressContract.View>() {
    override fun createModel(): ConfirmAddressContract.Model = ConfirmAddressModel()

    override fun getAddress() {
        mModel.getAddress(object : HttpObserver<ConfirmAddressResult>() {
            override fun onSuccess(bean: ConfirmAddressResult, msg: String) {
                mView?.getAddressSuccess(bean, msg)
            }

            override fun onFailure(code: String, msg: String) {
                mView?.getAdderssFailure(msg)
            }

        })
    }
}
