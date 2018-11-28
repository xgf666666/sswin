package com.micropole.sxwine.module.personal.mvp.presenter

import com.example.mvpframe.BaseMvpPresenter
import com.micropole.sxwine.module.personal.mvp.contract.FeedbackContract
import com.micropole.sxwine.module.personal.mvp.model.FeedbackModel
import com.micropole.sxwine.utils.network.HttpObserver

/**
 * Created by JohnnyH on 2018/6/15.
 */
class FeedbackPresenter : BaseMvpPresenter<FeedbackContract.Model,FeedbackContract.View>(),FeedbackContract.Presenter {
    override fun feedback(content: String?) {
        mModel?.feedback(content,object : HttpObserver<Any>(){
            override fun onSuccess(bean: Any, msg: String) {
                mView?.onFeedbackSuccess(msg)
            }

            override fun onFailure(code: String, msg: String) {
                mView?.onFeedbackFailure(msg)
            }

        })
    }

    override fun createModel(): FeedbackContract.Model =FeedbackModel()
}