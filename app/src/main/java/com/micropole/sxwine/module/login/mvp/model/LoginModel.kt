package com.micropole.sxwine.module.login.mvp.model

import com.darkhorse.httphelper.transformer.RxTransformer
import com.micropole.sxwine.bean.LoginResult
import com.micropole.sxwine.module.login.mvp.contract.LoginContract
import com.micropole.sxwine.utils.network.API
import com.micropole.sxwine.utils.network.HttpObserver

/**
 * Description:
 * Created by DarkHorse on 2018/5/24.
 */
class LoginModel : LoginContract.Model {
    override fun login(phone: String, pwd: String, observer: HttpObserver<LoginResult>) {

    }
}
