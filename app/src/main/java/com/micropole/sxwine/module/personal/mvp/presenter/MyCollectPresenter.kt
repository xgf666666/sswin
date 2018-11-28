package com.micropole.sxwine.module.personal.mvp.presenter

import com.example.mvpframe.BaseMvpPresenter
import com.micropole.sxwine.module.personal.Bean.MyCollectEntity
import com.micropole.sxwine.module.personal.mvp.contract.MyCollectContract
import com.micropole.sxwine.module.personal.mvp.model.MyCollectModel
import com.micropole.sxwine.utils.network.HttpObserver

/**
 * Created by JohnnyH on 2018/6/12.
 */
class MyCollectPresenter : BaseMvpPresenter<MyCollectContract.Model,MyCollectContract.View>(),MyCollectContract.Presenter {
    override fun createModel(): MyCollectContract.Model = MyCollectModel()

    override fun loadData(isRefresh: Boolean, isFirstLoading: Boolean, page: String?, per_page: String?) {
        mModel.loadData(page,per_page,object : HttpObserver<List<MyCollectEntity>>(){
            override fun onSuccess(bean: List<MyCollectEntity>, msg: String) {
                if (isRefresh){
                    mView?.setData(bean)
                }else {
                    mView?.addData(bean)
                }
            }

            override fun onFailure(code: String, msg: String) {
                mView?.onDataFailure(msg,true)
            }

        })
    }
}