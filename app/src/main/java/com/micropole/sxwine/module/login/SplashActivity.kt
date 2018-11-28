package com.micropole.sxwine.module.login

import android.content.Intent
import com.darkhorse.preferencesmanager.PreferencesHelper
import com.example.mvpframe.BaseMvpActivity
import com.micropole.sxwine.R
import com.micropole.sxwine.common.Constants
import com.micropole.sxwine.module.login.bean.ADEntity
import com.micropole.sxwine.module.login.mvp.contract.SplashContract
import com.micropole.sxwine.module.login.mvp.presenter.SplashPresenter
import java.util.*

/**
 * Created by JohnnyH on 2018/6/6.
 */
class SplashActivity : BaseMvpActivity<SplashContract.Model,SplashContract.View,SplashPresenter>(),SplashContract.View {

    override fun createPresenter(): SplashPresenter = SplashPresenter()

    override fun getLayoutId(): Int = R.layout.activity_splash

    override fun preSetContentView() {
        super.preSetContentView()
    }

    override fun initView() {
        mPresenter.loadData()
        initSplash()
    }

    override fun initListener() {

    }

    override fun initData() {

    }

    private fun initSplash(){
        Timer().schedule(
                object : TimerTask() {
                    override fun run() {
                        if (PreferencesHelper[Constants.IS_FIRST_INSTALL,true] as Boolean){
                            PreferencesHelper.put(Constants.IS_FIRST_INSTALL,false)
                            val intent = Intent(mContext, GuidePageActivity::class.java)
                            startActivity(intent)
                            finish()
                        }else {
                            if (PreferencesHelper[Constants.IS_LOGIN, false] as Boolean){
                                val intent = Intent(mContext, ADActivity::class.java)
                                startActivity(intent)
                                finish()
                            }else{
                                val intent = Intent(mContext, LoginActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                        }


                    }
                },
                1500
        )
    }

    /**
     * 广告
     */
    override fun onGetAdSuccess(entity: ADEntity?) {
        PreferencesHelper.put(Constants.AD_ENTITY,entity!!)
    }

    override fun onGetAdFailure(err: String?) {

    }
}