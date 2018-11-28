package com.micropole.sxwine.module.personal.mvp.presenter

import com.example.mvpframe.BaseMvpPresenter
import com.micropole.sxwine.module.personal.Bean.OrganizationEarningsEntity
import com.micropole.sxwine.module.personal.mvp.contract.OrganizationEarningsContract
import com.micropole.sxwine.module.personal.mvp.model.OrganizationEarningsModel
import com.micropole.sxwine.utils.network.HttpObserver

/**
 * Created by JohnnyH on 2018/6/14.
 */
class OrganizationEarningsPresenter : BaseMvpPresenter<OrganizationEarningsContract.Model,OrganizationEarningsContract.View>(),OrganizationEarningsContract.Presenter {

    override fun createModel(): OrganizationEarningsContract.Model = OrganizationEarningsModel()

    override fun loadData(isRefresh: Boolean, isFirstLoading: Boolean, page: String?, per_page: String?,type: String?) {
        mModel.loadData(page,per_page,type,object : HttpObserver<List<OrganizationEarningsEntity>>(){
            override fun onSuccess(bean: List<OrganizationEarningsEntity>, msg: String) {
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