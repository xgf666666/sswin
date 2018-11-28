package com.micropole.sxwine.module.personal


import android.content.Intent
import android.net.Uri
import android.support.v7.app.AlertDialog
import android.view.View
import com.blankj.utilcode.util.PermissionUtils
import com.example.mvpframe.BaseMvpActivity
import com.micropole.sxwine.R
import com.micropole.sxwine.base.hideLoadingDialog
import com.micropole.sxwine.base.initToolBar
import com.micropole.sxwine.base.showLoadingDialog
import com.micropole.sxwine.base.toast
import com.micropole.sxwine.module.personal.Bean.ContactServiceEntity
import com.micropole.sxwine.module.personal.mvp.contract.ContactServiceContract
import com.micropole.sxwine.module.personal.mvp.presenter.ContactServicePresenter
import com.micropole.sxwine.module.personal.web.Main7Activity
import kotlinx.android.synthetic.main.activity_contact_service.*
import kotlinx.android.synthetic.main.item_toolbar.*

/**
 * Created by JohnnyH on 2018/6/12.
 */
class ContactServiceActivity : BaseMvpActivity<ContactServiceContract.Model,ContactServiceContract.View,ContactServicePresenter>(),ContactServiceContract.View, View.OnClickListener {

    override fun createPresenter(): ContactServicePresenter = ContactServicePresenter()

    override fun getLayoutId(): Int = R.layout.activity_contact_service

    override fun initView() {
        initToolBar(getString(R.string.tv_service))
        toolbar.setNavigationOnClickListener { finish() }
        btn_online.setOnClickListener(this)
        btn_phone.setOnClickListener(this)
    }

    override fun initListener() {

    }

    override fun initData() {

    }

    override fun onClick(view: View?) {
        when(view!!.id){
            R.id.btn_online->{//在线客服
                showLoadingDialog()
                mPresenter.serviceOnline()
            }
            R.id.btn_phone->{//拨打电话
                PermissionUtils.permission(android.Manifest.permission.CALL_PHONE)
                        .callback(object : PermissionUtils.SimpleCallback{
                            override fun onGranted() {
                                showLoadingDialog()
                                mPresenter.consumeService()
                            }

                            override fun onDenied() {
                                toast(getString(R.string.tv_avater_detials))
                            }

                        })
                        .rationale { shouldRequest -> shouldRequest!!.again(true) }
                        .request()
            }
        }
    }

    /**
     * 在线客服
     */
    override fun onServiceOnlineSucess(data: ContactServiceEntity?) {
        hideLoadingDialog()
        val intent = Intent(mContext, Main7Activity::class.java)
        intent.putExtra("title",data!!.name)
        intent.putExtra("Url",data!!.value)
        startActivity(intent)

    }

    override fun onServiceOnlineFailure(err: String?) {
        hideLoadingDialog()
        toast(err!!)
    }

    /**
     * 拨打电话
     */
    private lateinit var mAlertDialog :AlertDialog
    override fun onConsumeServiceSuccess(data: ContactServiceEntity?) {
        hideLoadingDialog()
        val builder = AlertDialog.Builder(mContext)
                .setMessage(data!!.value)
                .setNegativeButton(getString(R.string.tv_cancel)) { p0, p1 ->
                    mAlertDialog.dismiss()
                }
                .setPositiveButton(getString(R.string.tv_call_phone)) { p0, p1 ->
                    val intent = Intent("android.intent.action.CALL", Uri.parse("tel:" + data!!.value))
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                    mAlertDialog.dismiss()
                }
        mAlertDialog = builder.create()
        mAlertDialog.show()
    }

    override fun onConsumeServiceFailure(err: String?) {
        hideLoadingDialog()
        toast(err!!)
    }

}