package com.micropole.sxwine.module.home.mvp.contract

import com.example.mvpframe.BaseMvpView
import com.micropole.sxwine.bean.ClassifyBean
import com.micropole.sxwine.utils.network.HttpObserver

/**
 * Description:
 * Created by DarkHorse on 2018/5/31.
 */
interface MoreClassifyContract {
    interface Model {
        fun getClassify(page: String, httpObserver: HttpObserver<ArrayList<ClassifyBean>>)
    }

    interface View : BaseMvpView {
        fun onSuccess(result: ArrayList<ClassifyBean>, msg: String)

        fun onFailure(err: String)
    }

    interface Presenter {
        fun getClassify(page: String)
    }
}
