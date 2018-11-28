package com.micropole.sxwine.module.personal.mvp.presenter

import com.example.mvpframe.BaseMvpPresenter
import com.micropole.sxwine.module.personal.Bean.FQAEntity
import com.micropole.sxwine.module.personal.mvp.contract.FQAContract
import com.micropole.sxwine.module.personal.mvp.model.FQAModel
import com.micropole.sxwine.utils.network.HttpObserver

/**
 * Created by JohnnyH on 2018/6/12.
 */
class FQAPresenter : BaseMvpPresenter<FQAContract.Model,FQAContract.View>(),FQAContract.Presenter {

    override fun loadData(isRefresh: Boolean, isFirstLoading: Boolean) {
        mModel.loadData(object : HttpObserver<List<FQAEntity>>(){
            override fun onSuccess(bean: List<FQAEntity>, msg: String) {
                mView?.setData(bean)
            }

            override fun onFailure(code: String, msg: String) {
                mView?.onDataFailure(msg,isFirstLoading)
            }

        })
    }

    override fun createModel(): FQAContract.Model = FQAModel()
}