package com.micropole.sxwine.module.home.mvp.contract

import com.example.mvpframe.BaseMvpView
import com.micropole.sxwine.bean.MessageBean
import com.micropole.sxwine.utils.network.HttpObserver

/**
 * Description:
 * Created by DarkHorse on 2018/9/6.
 */
interface MessageContract {
    interface Model {
        fun getMessageList(page: Int, httpObserver: HttpObserver<ArrayList<MessageBean>>)
    }

    interface View : BaseMvpView {
        fun onSuccess(list: ArrayList<MessageBean>, msg: String)

        fun onFailure(error: String)
    }

    interface Presenter {
        fun getMessageList(page: Int)
    }
}
