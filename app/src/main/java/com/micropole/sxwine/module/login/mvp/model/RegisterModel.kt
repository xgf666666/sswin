package com.micropole.sxwine.module.login.mvp.model

import com.darkhorse.httphelper.transformer.RxTransformer
import com.micropole.sxwine.module.login.bean.LoginEntity
import com.micropole.sxwine.module.login.mvp.contract.RegisterContract
import com.micropole.sxwine.module.personal.Bean.UserInfoEntity
import com.micropole.sxwine.utils.network.API
import com.micropole.sxwine.utils.network.HttpObserver

/**
 * Created by JohnnyH on 2018/6/6.
 */
class RegisterModel : RegisterContract.Model {
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

    override fun register(mobile: String?, password: String?, password_confirmation: String?,
                          verify_code: String?, recommend_code: String?,credentials_type: String?,
                          credentials_no: String?,real_name: String?,message_code: String?,
                          httpObserver: HttpObserver<LoginEntity>?) {
        API.service.register(mobile,password,password_confirmation,verify_code,recommend_code,
                credentials_type,credentials_no,real_name,message_code)
                .compose(RxTransformer.io_main())
                .subscribe(httpObserver!!)
    }
}