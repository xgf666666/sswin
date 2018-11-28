package com.micropole.sxwine.module.personal.mvp.contract

import com.example.mvpframe.BaseMvpView
import com.micropole.sxwine.bean.MemberBean
import com.micropole.sxwine.bean.MyTeamResult
import com.micropole.sxwine.utils.network.HttpObserver

/**
 * Created by JohnnyH on 2018/6/12.
 */

class MyTeamContract {

    interface View : BaseMvpView {
        fun onSuccess(result: MyTeamResult, msg: String)

        fun onFailure(msg: String)
    }

    interface Presenter {
        fun getData(type: Int,page:Int)
    }

    interface Model {
        fun getData(type: Int,page:Int, httpObserver: HttpObserver<MyTeamResult>)
    }
}
