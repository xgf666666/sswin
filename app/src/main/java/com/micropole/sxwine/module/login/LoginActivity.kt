package com.micropole.sxwine.module.login

import android.content.Intent
import android.view.KeyEvent
import android.view.View
import com.blankj.utilcode.util.ActivityUtils
import com.example.baseframe.BaseActivity
import com.micropole.sxwine.R
import com.micropole.sxwine.base.toast
import kotlinx.android.synthetic.main.activity_login.*
import java.util.*

class LoginActivity : BaseActivity(), View.OnClickListener {

    override fun getLayoutId(): Int = R.layout.activity_login

    override fun initView() {
        btn_register.setOnClickListener(this)
        btn_login.setOnClickListener(this)
    }

    override fun initListener() {

    }

    override fun initData() {

    }

    override fun onClick(view: View?) {
        when(view!!.id){
            R.id.btn_register->{//注册
                val intent = Intent(mContext, RegisterActivity::class.java)
                startActivity(intent)
            }
            R.id.btn_login->{//登录
                val intent = Intent(mContext, LoginActivity2::class.java)
                startActivity(intent)
            }
        }
    }

    private var isExit : Boolean = false

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            //双击退出
            back2exit()

            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    private fun back2exit() {
        if (isExit) {
            //System.exit(0)
            ActivityUtils.finishAllActivities()
        } else {
            isExit = true
            toast("再按一次退出应用")
            Timer().schedule(object : TimerTask() {
                override fun run() {
                    isExit = false
                }
            }, 1500)
        }
    }

}
