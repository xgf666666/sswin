package com.micropole.sxwine.module.home.mvp.contract

import com.example.mvpframe.BaseMvpView
import com.micropole.sxwine.bean.ConfirmAddressResult
import com.micropole.sxwine.utils.network.HttpObserver

/**
 * Description:
 * Created by DarkHorse on 2018/9/14.
 */
interface ConfirmAddressContract {
    interface Model{
        fun getAddress(httpObserver: HttpObserver<ConfirmAddressResult>)
    }

    interface View:BaseMvpView{
        fun getAddressSuccess(result: ConfirmAddressResult, msg: String)

        fun getAdderssFailure(error: String)
    }

    interface Presenter{
        fun getAddress()
    }
}
