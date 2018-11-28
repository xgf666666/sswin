package com.micropole.sxwine.module.home.mvp.presenter

import com.example.mvpframe.BaseMvpPresenter
import com.example.mvpframe.BaseMvpView
import com.micropole.sxwine.bean.MessageBean
import com.micropole.sxwine.module.home.mvp.contract.MessageContract
import com.micropole.sxwine.module.home.mvp.model.MessageModel
import com.micropole.sxwine.utils.network.HttpObserver

/**
 * Description:
 * Created by DarkHorse on 2018/9/6.
 */
class MessagePresenter : MessageContract.Presenter, BaseMvpPresenter<MessageContract.Model, MessageContract.View>() {

    override fun createModel(): MessageContract.Model = MessageModel()

    override fun getMessageList(page: Int) {
        mModel.getMessageList(page, object : HttpObserver<ArrayList<MessageBean>>() {
            override fun onSuccess(bean: ArrayList<MessageBean>, msg: String) {
                mView?.onSuccess(bean, msg)
            }

            override fun onFailure(code: String, msg: String) {
                mView?.onFailure(msg)
            }
        })
    }

}
