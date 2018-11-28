package com.micropole.sxwine.module.login.mvp.model

import com.darkhorse.httphelper.transformer.RxTransformer
import com.micropole.sxwine.module.login.bean.LoginEntity
import com.micropole.sxwine.module.login.mvp.contract.Login2Contract
import com.micropole.sxwine.module.personal.Bean.UserInfoEntity
import com.micropole.sxwine.utils.network.API
import com.micropole.sxwine.utils.network.HttpObserver

/**
 * Description:
 * Created by DarkHorse on 2018/5/24.
 */
class Login2Model : Login2Contract.Model {
    override fun getUserInfo(httpObserver: HttpObserver<UserInfoEntity>) {
        API.service.getUserInfo()
                .compose(RxTransformer.io_main())
                .subscribe(httpObserver)
    }

    override fun pwdLogin(mobile: String, password: String, httpObserver: HttpObserver<LoginEntity>) {
        API.service.pwdLogin(mobile,password)
                .compose(RxTransformer.io_main())
                .subscribe(httpObserver)
    }

}
