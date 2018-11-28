package com.micropole.sxwine.module.personal.mvp.presenter

import com.example.mvpframe.BaseMvpPresenter
import com.micropole.sxwine.module.personal.Bean.AccumulateEarningsEntity
import com.micropole.sxwine.module.personal.Bean.EarningsDetails1Entity
import com.micropole.sxwine.module.personal.mvp.contract.EarningDetails1Contract
import com.micropole.sxwine.module.personal.mvp.model.EarningsDetails1Model
import com.micropole.sxwine.utils.network.HttpObserver

/**
 * Created by JohnnyH on 2018/6/13.
 */
class EarningsDetails1Presenter : BaseMvpPresenter<EarningDetails1Contract.Model,EarningDetails1Contract.View>(),EarningDetails1Contract.Presenter {
    override fun getAccumulateEarnings() {
        mModel.getAccumulateEarnings(object :HttpObserver<AccumulateEarningsEntity>(){
            override fun onSuccess(bean: AccumulateEarningsEntity, msg: String) {
                mView?.setAccumulateEarnings(bean)
            }

            override fun onFailure(code: String, msg: String) {
                mView?.onDataFailure(msg)
            }

        })
    }

    override fun loadData(type: String?) {
        mModel.loadData(type,object : HttpObserver<EarningsDetails1Entity>(){
            override fun onSuccess(bean: EarningsDetails1Entity, msg: String) {
                mView?.setData(bean)
            }

            override fun onFailure(code: String, msg: String) {
                mView?.onDataFailure(msg)
            }

        })
    }

    override fun createModel(): EarningDetails1Contract.Model = EarningsDetails1Model()
}