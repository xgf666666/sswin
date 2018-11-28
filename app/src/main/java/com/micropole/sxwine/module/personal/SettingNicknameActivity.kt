package com.micropole.sxwine.module.personal

import android.content.Intent
import android.text.TextUtils
import com.example.mvpframe.BaseMvpActivity
import com.micropole.sxwine.R
import com.micropole.sxwine.base.hideLoadingDialog
import com.micropole.sxwine.base.initToolBar
import com.micropole.sxwine.base.showLoadingDialog
import com.micropole.sxwine.base.toast
import com.micropole.sxwine.module.personal.mvp.contract.SettingNicknameContract
import com.micropole.sxwine.module.personal.mvp.presenter.SettingNickNamePresenter
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.activity_setting_naickname.*

/**
 * Created by JohnnyH on 2018/6/15.
 * 设置昵称
 */
class SettingNicknameActivity : BaseMvpActivity<SettingNicknameContract.Model,SettingNicknameContract.View,SettingNickNamePresenter>(),SettingNicknameContract.View {

    private lateinit var mNickname : String

    override fun createPresenter(): SettingNickNamePresenter = SettingNickNamePresenter()
    override fun getLayoutId(): Int = R.layout.activity_setting_naickname

    override fun initView() {
        mNickname=intent.getStringExtra("nickname")
        initToolBar(getString(R.string.alter_nickname))
        toolbar.setNavigationOnClickListener { finish() }
        if (!TextUtils.isEmpty(mNickname)){
            ed_nickname.setText(mNickname)
        }else{
            ed_nickname.hint = getString(R.string.tv_input_nickname)
        }

    }

    override fun initData() {

    }

    override fun initListener() {
        btn_confirm.setOnClickListener{//确认
            showLoadingDialog()
            mPresenter.alterNickname(ed_nickname.text.toString().trim())
        }
    }



    override fun onAlterNicknameSuccess(msg: String?) {
        hideLoadingDialog()
        toast(msg!!)
        val intent = Intent()
        intent.putExtra("nickname",ed_nickname.text.toString().trim())
        setResult(NICKNAME_RESULT_CODE,intent)
        finish()
    }

    override fun onAlterNicknameFailure(err: String?) {
        hideLoadingDialog()
        toast(err!!)
    }

    companion object {
        val NICKNAME_RESULT_CODE : Int =10001
    }
}