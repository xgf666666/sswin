package com.micropole.sxwine.module.login.mvp.model

import com.darkhorse.httphelper.transformer.RxTransformer
import com.micropole.sxwine.module.login.bean.LoginEntity
import com.micropole.sxwine.module.login.mvp.contract.CodeLoginContract
import com.micropole.sxwine.module.personal.Bean.UserInfoEntity
import com.micropole.sxwine.utils.network.API
import com.micropole.sxwine.utils.network.HttpObserver

/**
 * Created by JohnnyH on 2018/6/7.
 */
class CodeLoginModel : CodeLoginContract.Model {
    override fun getUserInfo(httpObserver: HttpObserver<UserInfoEntity>?) {
        API.service.getUserInfo()
                .compose(RxTransformer.io_main())
                .subscribe(httpObserver!!)
    }

    override fun sendCode(mobile: String?, type: String?,message_code: String?, httpObserver: HttpObserver<Any>?) {
        API.service.sendCode(mobile,type,message_code)
                .compose(RxTransformer.io_main())
                .subscribe(httpObserver!!)
    }

    override fun codeLogin(mobile: String?, verify_code: String?,message_code: String?, httpObserver: HttpObserver<LoginEntity>?) {
        API.service.codeLogin(mobile,verify_code,message_code)
                .compose(RxTransformer.io_main())
                .subscribe(httpObserver!!)
    }
}