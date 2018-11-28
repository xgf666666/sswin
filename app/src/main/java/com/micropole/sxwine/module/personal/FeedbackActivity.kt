package com.micropole.sxwine.module.personal

import android.text.TextUtils
import com.example.mvpframe.BaseMvpActivity
import com.micropole.sxwine.R
import com.micropole.sxwine.base.hideLoadingDialog
import com.micropole.sxwine.base.initToolBar
import com.micropole.sxwine.base.showLoadingDialog
import com.micropole.sxwine.base.toast
import com.micropole.sxwine.module.personal.mvp.contract.FeedbackContract
import com.micropole.sxwine.module.personal.mvp.presenter.FeedbackPresenter
import kotlinx.android.synthetic.main.activity_feedback.*
import kotlinx.android.synthetic.main.item_toolbar.*

/**
 * Created by JohnnyH on 2018/6/15.
 * 意见反馈
 */
class FeedbackActivity : BaseMvpActivity<FeedbackContract.Model,FeedbackContract.View,FeedbackPresenter>(),FeedbackContract.View {

    override fun createPresenter(): FeedbackPresenter = FeedbackPresenter()

    override fun getLayoutId(): Int = R.layout.activity_feedback

    override fun initView() {
        initToolBar(getString(R.string.tv_feedback))
        toolbar.setNavigationOnClickListener { finish() }
    }

    override fun initData() {

    }

    override fun initListener() {
        btn_submit.setOnClickListener{
            val content = ed_content.text.toString().trim()
            if (TextUtils.isEmpty(content)){
                toast("内容不能为空")
            }
            showLoadingDialog()
            mPresenter.feedback(content)
        }
    }

    override fun onFeedbackSuccess(msg: String?) {
        hideLoadingDialog()
        toast(msg!!)
        finish()
    }

    override fun onFeedbackFailure(err: String?) {
        hideLoadingDialog()
        toast(err!!)
    }
}