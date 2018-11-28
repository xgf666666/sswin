package com.micropole.sxwine.module.personal.mvp.model

import com.darkhorse.httphelper.transformer.RxTransformer
import com.micropole.sxwine.module.personal.mvp.contract.SettingNicknameContract
import com.micropole.sxwine.utils.network.API
import com.micropole.sxwine.utils.network.HttpObserver

/**
 * Created by JohnnyH on 2018/6/15.
 */
class SettingNicknameModel : SettingNicknameContract.Model {
    override fun alterNickname(nickname: String?, httpObserver: HttpObserver<Any>?) {
        API.service.alterNickname(nickname)
                .compose(RxTransformer.io_main())
                .subscribe(httpObserver!!)
    }
}