package com.micropole.sxwine.module.personal.mvp.model

import com.darkhorse.httphelper.transformer.RxTransformer
import com.micropole.sxwine.bean.MemberBean
import com.micropole.sxwine.bean.MyTeamResult
import com.micropole.sxwine.module.personal.mvp.contract.MyTeamContract
import com.micropole.sxwine.utils.network.API
import com.micropole.sxwine.utils.network.HttpObserver

/**
 * Created by JohnnyH on 2018/6/12.
 */
class MyTeamModel : MyTeamContract.Model {
    override fun getData(type: Int, page: Int, httpObserver: HttpObserver<MyTeamResult>) {
        API.service.getMyTeam(type,page)
                .compose(RxTransformer.io_main())
                .subscribe(httpObserver)
    }
}