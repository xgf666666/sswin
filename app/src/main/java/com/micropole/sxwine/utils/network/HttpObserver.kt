package com.micropole.sxwine.utils.network

import com.micropole.sxwine.bean.BaseResponseBean
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * Description:
 * Created by DarkHorse on 2018/5/24.
 */
abstract class HttpObserver<T> : Observer<BaseResponseBean<T>> {
    abstract fun onSuccess(bean: T, msg: String)

    abstract fun onFailure(code: String, msg: String)

    override fun onComplete() {
//        AppManager.currentActivity().hideLoadingDialog()
    }

    override fun onSubscribe(d: Disposable) {
//        AppManager.currentActivity().showLoadingDialog()
    }

    override fun onNext(t: BaseResponseBean<T>) {
        if (API.SUCCESS_STATUS == t.status) {
            onSuccess(t.data, t.msg)
        } /*else {
            onFailure(t.code, t.msg)
        }*/
    }

    override fun onError(e: Throwable) {
        onFailure("1", e.message!!)
    }
}
