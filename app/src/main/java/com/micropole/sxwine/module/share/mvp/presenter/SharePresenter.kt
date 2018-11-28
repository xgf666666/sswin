package com.micropole.sxwine.module.share.mvp.presenter

import com.example.mvpframe.BaseMvpPresenter
import com.micropole.sxwine.bean.ShareBean
import com.micropole.sxwine.module.share.mvp.contract.ShareContract
import com.micropole.sxwine.module.share.mvp.model.ShareModel
import com.micropole.sxwine.utils.network.HttpObserver

/**
 * Description:
 * Created by DarkHorse on 2018/6/22.
 */
class SharePresenter : ShareContract.Presenter, BaseMvpPresenter<ShareContract.Model, ShareContract.View>() {
    override fun createModel(): ShareContract.Model = ShareModel()

    override fun getShareData() {
        mModel.getShareData(object : HttpObserver<ShareBean>() {
            override fun onSuccess(bean: ShareBean, msg: String) {
                mView?.onSuccess(bean)
            }

            override fun onFailure(code: String, msg: String) {
                mView?.onFailure(msg)
            }
        })
    }
}
