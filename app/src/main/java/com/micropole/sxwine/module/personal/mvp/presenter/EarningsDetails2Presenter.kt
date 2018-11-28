package com.micropole.sxwine.module.personal.mvp.presenter

import com.example.mvpframe.BaseMvpPresenter
import com.micropole.sxwine.module.personal.Bean.EarningsDetails2Entity
import com.micropole.sxwine.module.personal.mvp.contract.EarningsDetails2Contract
import com.micropole.sxwine.module.personal.mvp.model.EarningsDetails2Model
import com.micropole.sxwine.utils.network.HttpObserver

/**
 * Created by JohnnyH on 2018/6/13.
 */
class EarningsDetails2Presenter : BaseMvpPresenter<EarningsDetails2Contract.Model,EarningsDetails2Contract.View>(),EarningsDetails2Contract.Presenter {
    override fun createModel(): EarningsDetails2Contract.Model = EarningsDetails2Model()

    override fun loadData(isRefresh: Boolean, isFirstLoading: Boolean, type: String?, page: String?, per_page: String?) {
        mModel.loadData(type,page,per_page,object : HttpObserver<List<EarningsDetails2Entity>>(){
            override fun onSuccess(bean: List<EarningsDetails2Entity>, msg: String) {
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
}