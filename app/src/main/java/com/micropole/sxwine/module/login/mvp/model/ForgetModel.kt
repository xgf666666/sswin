package com.micropole.sxwine.module.login.mvp.model

import com.darkhorse.httphelper.transformer.RxTransformer
import com.micropole.sxwine.module.login.bean.LoginEntity
import com.micropole.sxwine.module.login.mvp.contract.ForgetContract
import com.micropole.sxwine.utils.network.API
import com.micropole.sxwine.utils.network.HttpObserver

/**
 * Description:
 * Created by DarkHorse on 2018/5/25.
 */
class ForgetModel : ForgetContract.Model {


    override fun sendCode(mobile: String, type: String,message_code:String, httpObserver: HttpObserver<Any>) {
        API.service.sendCode(mobile,type,message_code)
                .compose(RxTransformer.io_main())
                .subscribe(httpObserver)
    }

    override fun resetPwd(mobile: String, password: String, password_confirmation: String, verify_code: String,
                          message_code : String,httpObserver: HttpObserver<LoginEntity>) {
        API.service.resetPwd(mobile,password,password_confirmation,verify_code,message_code)
                .compose(RxTransformer.io_main())
                .subscribe(httpObserver)
    }

}
