package com.micropole.sxwine.module.personal

import android.os.Bundle
import android.view.View
import com.example.mvpframe.BaseMvpActivity
import com.micropole.sxwine.R
import com.micropole.sxwine.R.id.*
import com.micropole.sxwine.adapter.MyTeamAdapter
import com.micropole.sxwine.base.hideLoadingDialog
import com.micropole.sxwine.base.initToolBar
import com.micropole.sxwine.base.showLoadingDialog
import com.micropole.sxwine.base.toast
import com.micropole.sxwine.bean.MemberBean
import com.micropole.sxwine.bean.MyTeamResult
import com.micropole.sxwine.module.personal.mvp.contract.MyTeamContract
import com.micropole.sxwine.module.personal.mvp.presenter.MyTeamPresenter
import com.micropole.sxwine.utils.network.API
import kotlinx.android.synthetic.main.activity_my_team.*

/**
 * Created by JohnnyH on 2018/6/12.
 */
class MyTeamActivity : BaseMvpActivity<MyTeamContract.Model, MyTeamContract.View, MyTeamPresenter>(), MyTeamContract.View, View.OnClickListener {

    private lateinit var mMemberList: ArrayList<MemberBean>
    private lateinit var mTeamAdapter: MyTeamAdapter
    private var mType = 1
    private var mPage = 1

    override fun createPresenter(): MyTeamPresenter = MyTeamPresenter()

    override fun getLayoutId(): Int = R.layout.activity_my_team

    override fun initView() {
        initToolBar(getString(R.string.my_team))
        initRecyclerView()
    }

    private fun initRecyclerView() {
        mMemberList = ArrayList()
        mTeamAdapter = MyTeamAdapter(R.layout.item_rcv_member, mMemberList)
        mTeamAdapter.setOnItemClickListener({ adapter, view, position ->
            val bundle = Bundle()
            bundle.putString("ic_launcher", API.HOST + mMemberList[position].avatar)
            bundle.putString("name", mMemberList[position].nickname)
            bundle.putString("vip", mMemberList[position].vip_name)
            bundle.putString("id", mMemberList[position].show_id)
            startActivity(VipDetail::class.java, bundle)
        })
        mTeamAdapter.setOnLoadMoreListener({
            if (mPage != -1) {
                showLoadingDialog()
                mPresenter.getData(mType, ++mPage)
            }else{
                mTeamAdapter.loadMoreEnd()
            }
        }, rcv_member)
        rcv_member.adapter = mTeamAdapter
    }

    override fun initListener() {
        type_1.setOnClickListener(this)
        type_2.setOnClickListener(this)
        type_3.setOnClickListener(this)
    }

    override fun initData() {
        showLoadingDialog()
        selectorType(1)
    }

    override fun onSuccess(result: MyTeamResult, msg: String) {
        hideLoadingDialog()
        if (mPage <= 1) {
            mMemberList.clear()
        } else {
            if (result.vips.isEmpty()) {
                mPage = -1
            } else {
                mTeamAdapter.loadMoreComplete()
            }
        }
        mMemberList.addAll(result.vips)
        mTeamAdapter.setNewData(mMemberList)
        tv_organization_count.text = result.organization_count + getString(R.string.person)
        tv_relative_count.text = result.under_count + getString(R.string.person)
    }

    override fun onFailure(msg: String) {
        hideLoadingDialog()
        mPage = -1
        mTeamAdapter.loadMoreComplete()
        toast(msg)
    }

    fun selectorType(i: Int) {
        type_1.isSelected = false
        type_2.isSelected = false
        type_3.isSelected = false

        when (i) {
            1 -> {
                type_1.isSelected = true
                mType = 1
                mPage = 1
                showLoadingDialog()
                mPresenter.getData(mType, mPage)
            }
            2 -> {
                type_2.isSelected = true
                mType = 2
                mPage = 1
                showLoadingDialog()
                mPresenter.getData(mType, mPage)
            }
            3 -> {
                type_3.isSelected = true
                mType = 3
                mPage = 1
                showLoadingDialog()
                mPresenter.getData(mType, mPage)
            }
        }
    }

    override fun onClick(v: View?) {
        when (v) {
            type_1 -> selectorType(1)
            type_2 -> selectorType(2)
            type_3 -> selectorType(3)
        }
    }

}