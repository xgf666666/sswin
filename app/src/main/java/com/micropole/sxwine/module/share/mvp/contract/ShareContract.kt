package com.micropole.sxwine.module.share.mvp.contract

import com.example.mvpframe.BaseMvpView
import com.micropole.sxwine.bean.ShareBean
import com.micropole.sxwine.utils.network.HttpObserver

/**
 * Description:
 * Created by DarkHorse on 2018/6/22.
 */
interface ShareContract {
    interface Model {
        fun getShareData(httpObserver: HttpObserver<ShareBean>)
    }

    interface View : BaseMvpView {
        fun onSuccess(shareBean: ShareBean)

        fun onFailure(err: String)
    }

    interface Presenter {
        fun getShareData()
    }
}
