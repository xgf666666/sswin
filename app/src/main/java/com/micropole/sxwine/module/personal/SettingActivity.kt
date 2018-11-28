package com.micropole.sxwine.module.personal

import android.content.Intent
import android.support.v7.app.AlertDialog
import android.view.View
import com.example.mvpframe.BaseMvpActivity
import com.micropole.sxwine.R
import com.micropole.sxwine.base.initToolBar
import com.micropole.sxwine.module.personal.mvp.contract.SettingContract
import com.micropole.sxwine.module.personal.mvp.presenter.SettingPresenter
import kotlinx.android.synthetic.main.activity_setting.*
import kotlinx.android.synthetic.main.item_toolbar.*

/**
 * Created by JohnnyH on 2018/6/8.
 * 设置
 */
class SettingActivity : BaseMvpActivity<SettingContract.Model,SettingContract.View,SettingPresenter>(),SettingContract.View, View.OnClickListener {

    override fun createPresenter(): SettingPresenter = SettingPresenter()

    override fun getLayoutId(): Int = R.layout.activity_setting

    override fun initView() {
        rl_address_manager.setOnClickListener(this)
        log_out.setOnClickListener(this)
        rl_set_login_pwd.setOnClickListener(this)
        rl_set_pay_pwd.setOnClickListener(this)
        rl_feedback.setOnClickListener(this)
        rl_about.setOnClickListener(this)
        btn_cut.setOnClickListener(this)
    }

    override fun initListener() {
        initToolBar(getString(R.string.tv_setting))
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    override fun initData() {

    }

    override fun onClick(view: View?) {
        when(view!!.id){
            R.id.rl_set_login_pwd->{//修改/设置登录密码
                val intent = Intent(mContext, SettingLoginPwdActivity::class.java)
                startActivity(intent)

            }
            R.id.rl_set_pay_pwd->{
                val intent = Intent(mContext, SettingPayPwdActivity::class.java)
                startActivity(intent)
            }
            R.id.rl_address_manager->{//地址管理
                val intent = Intent(mContext, AddressManagerActivity::class.java)
                startActivity(intent)
            }
            R.id.rl_feedback->{//意见反馈
                val intent = Intent(mContext, FeedbackActivity::class.java)
                startActivity(intent)
            }
            R.id.rl_about->{//关于我们
                val intent = Intent(mContext, AboutActivity::class.java)
                startActivity(intent)
            }
            R.id.btn_cut->{//切换账号
                setResult(1001)
                finish()
            }
            R.id.log_out->{//退出登录
                AlertDialog.Builder(mContext).setMessage(getString(R.string.confirm_log_out))
                        .setPositiveButton(getString(R.string.confirm)) { p0, p1 ->
                            setResult(1001)
                            finish()
                        }
                        .setNegativeButton(getString(R.string.tv_cancel)) { p0, p1 ->
                            p0.dismiss()
                        }
                        .create().show()
            }
        }
    }

}