package com.micropole.sxwine.module.personal.mvp.model

import com.darkhorse.httphelper.transformer.RxTransformer
import com.darkhorse.preferencesmanager.PreferencesHelper
import com.micropole.sxwine.common.Constants
import com.micropole.sxwine.module.personal.Bean.UserInfoEntity
import com.micropole.sxwine.module.personal.mvp.contract.SettingLoginPwdContract
import com.micropole.sxwine.utils.network.API
import com.micropole.sxwine.utils.network.HttpObserver

/**
 * Created by JohnnyH on 2018/6/15.
 */
class SettingLoginPwdModel : SettingLoginPwdContract.Model {

    override fun sendCode(mobile: String?, type: String?, httpObserver: HttpObserver<Any>?) {

        API.service.sendCode(mobile,type,(PreferencesHelper[Constants.USER_INFO, UserInfoEntity()] as UserInfoEntity).message_code )
                .compose(RxTransformer.io_main())
                .subscribe(httpObserver!!)
    }

    override fun alterOrSetLoginPwd(mobile: String?, password: String?, password_confirmation: String?, verify_code: String?, httpObserver: HttpObserver<Any>?) {
        API.service.alterOrSettingLoginPwd(mobile,password,password_confirmation,verify_code,(PreferencesHelper[Constants.USER_INFO, UserInfoEntity()] as UserInfoEntity).message_code)
                .compose(RxTransformer.io_main())
                .subscribe(httpObserver!!)
    }
}