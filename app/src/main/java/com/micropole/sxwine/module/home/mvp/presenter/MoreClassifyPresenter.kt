package com.micropole.sxwine.module.home.mvp.presenter

import com.example.mvpframe.BaseMvpPresenter
import com.micropole.sxwine.bean.ClassifyBean
import com.micropole.sxwine.module.home.mvp.contract.MoreClassifyContract
import com.micropole.sxwine.module.home.mvp.model.MoreClassifyModel
import com.micropole.sxwine.utils.network.HttpObserver

/**
 * Description:
 * Created by DarkHorse on 2018/5/31.
 */
class MoreClassifyPresenter internal constructor() : MoreClassifyContract.Presenter, BaseMvpPresenter<MoreClassifyContract.Model, MoreClassifyContract.View>() {
    override fun getClassify(page: String) {

        mModel.getClassify(page,object :HttpObserver<ArrayList<ClassifyBean>>(){
            override fun onSuccess(list: ArrayList<ClassifyBean>, msg: String) {
                mView?.onSuccess(list,msg)
            }

            override fun onFailure(code: String, msg: String) {
                mView?.onFailure(msg)
            }
        })
    }

    override fun createModel(): MoreClassifyContract.Model = MoreClassifyModel()
}
