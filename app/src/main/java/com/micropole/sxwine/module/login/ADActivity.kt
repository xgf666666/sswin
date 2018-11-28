package com.micropole.sxwine.module.login

import android.content.Intent
import android.text.TextUtils
import android.util.Log
import com.darkhorse.preferencesmanager.PreferencesHelper
import com.micropole.sxwine.MainActivity
import com.micropole.sxwine.R
import com.micropole.sxwine.base.loadADImg
import com.micropole.sxwine.common.Constants
import com.micropole.sxwine.module.login.bean.ADEntity
import com.micropole.sxwine.utils.network.API
import com.micropole.sxwine.widgets.CountDownProgressView
import kotlinx.android.synthetic.main.activity_ad.*

/**
 * Created by JohnnyH on 2018/8/28.
 */
class ADActivity : com.example.baseframe.BaseActivity() {

    private lateinit var mAdEntity: ADEntity

    override fun getLayoutId(): Int = R.layout.activity_ad

    override fun initView() {
        mAdEntity = PreferencesHelper[Constants.AD_ENTITY, ADEntity()] as ADEntity
        Log.e("Tag","API.HOST+mAdEntity.img="+API.HOST+mAdEntity.img)
        if(mAdEntity==null||TextUtils.isEmpty(mAdEntity.img)){
            finish()
        }else {
            iv_ad.loadADImg(mContext, API.HOST+mAdEntity.img)
        }

        countDownProgressView.setTimeMillis(5000)
        countDownProgressView.setText(getString(R.string.tv_skip))
        countDownProgressView.start()
        countDownProgressView.setProgressListener(object : CountDownProgressView.OnProgressListener{
            override fun onProgress(progress: Int) {
                if (0==progress){
                    val intent = Intent(mContext, MainActivity::class.java)
                    startActivity(intent)
                }
            }

        })

    }

    override fun initListener() {
        countDownProgressView.setOnClickListener {
            countDownProgressView.stop()
            val intent = Intent(mContext, MainActivity::class.java)
            startActivity(intent)
        }

        iv_ad.setOnClickListener{
            countDownProgressView.stop()
            val intent = Intent(mContext, MainActivity::class.java)
            intent.putExtra("flag","1")
            intent.putExtra("ADEntity",mAdEntity)
            startActivity(intent)
        }

    }

    override fun initData() {

    }

}