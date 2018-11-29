package com.micropole.sxwine.module.personal

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.darkhorse.preferencesmanager.PreferencesHelper
import com.example.mvpframe.BaseMvpFragment
import com.micropole.sxwine.MainActivity
import com.micropole.sxwine.R
import com.micropole.sxwine.base.hideLoadingDialog
import com.micropole.sxwine.base.loadImg
import com.micropole.sxwine.base.showLoadingDialog
import com.micropole.sxwine.common.Constants
import com.micropole.sxwine.module.login.LoginActivity
import com.micropole.sxwine.module.personal.Bean.MineItemEntity
import com.micropole.sxwine.module.personal.Bean.OrderStatusEntity
import com.micropole.sxwine.module.personal.Bean.UserInfoEntity
import com.micropole.sxwine.module.personal.adapter.MineAdapter
import com.micropole.sxwine.module.personal.mvp.contract.MineContract
import com.micropole.sxwine.module.personal.mvp.presenter.MinePresenter
import com.micropole.sxwine.utils.network.API
import kotlinx.android.synthetic.main.fragment_mine.view.*
import kotlinx.android.synthetic.main.view_mine_head.view.*

/**
 * Description:
 * Created by DarkHorse on 2018/5/28.
 */
class MineFragment : BaseMvpFragment<MineContract.Model, MineContract.View, MinePresenter>(), View.OnClickListener,MineContract.View {

    private lateinit var mAdapter : MineAdapter

    override fun createPresenter(): MinePresenter = MinePresenter()
    override fun getLayoutId(): Int = R.layout.fragment_mine

    private lateinit var mRootView : View
    override fun initView(rootView: View) {
        mRootView=rootView
        mRootView.iv_setting.setOnClickListener(this)
        mRootView.rec_my.layoutManager=LinearLayoutManager(mContext)
        mAdapter=MineAdapter(R.layout.item_mine_view,MineItemEntity.createList())
        mAdapter.addHeaderView(getHeaderView())
        mRootView.rec_my.adapter=mAdapter
    }

    private lateinit var mHeaderView : View
    private fun getHeaderView(): View? {
        mHeaderView = View.inflate(mContext, R.layout.view_mine_head, null)
        mHeaderView.rl_header.setOnClickListener(this)
        mHeaderView.rl_my_order.setOnClickListener(this)
        mHeaderView.rl_wait_pay.setOnClickListener(this)
        mHeaderView.rl_wait_send.setOnClickListener(this)
        mHeaderView.rl_wait_receive.setOnClickListener(this)
        mHeaderView.rl_complete.setOnClickListener(this)
        return mHeaderView
    }

    override fun initData() {
        mActivity.showLoadingDialog()
        mPresenter.getUserInfo()
        mPresenter.getOrderStatus()
    }

    override fun initListener(rootView: View) {
        mRootView.sw_mine.setOnRefreshListener {
            mPresenter.getUserInfo()
            mPresenter.getOrderStatus()
        }
        rootView.rec_my.addOnItemTouchListener(object : OnItemClickListener(){
            override fun onSimpleItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                val intent = Intent(mContext, mAdapter.data[position].activity)
                startActivity(intent)
            }
        })
    }

    override fun onResume() {
        super.onResume()
        mPresenter.getUserInfo()
        mPresenter.getOrderStatus()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        if (!hidden){
            mPresenter.getUserInfo()
            mPresenter.getOrderStatus()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==0&&resultCode==1001){//1001 设置页面返回
            (mContext as MainActivity).finish()
            PreferencesHelper.put(Constants.IS_LOGIN, false)
            val intent = Intent(mContext, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onClick(view: View?) {
        when(view!!.id){
            R.id.iv_setting->{//设置
                val intent = Intent(mContext, SettingActivity::class.java)
                startActivityForResult(intent,0)
            }
            R.id.rl_header->{//头像
                val intent = Intent(mContext, PersonalInfoActivity::class.java)
                startActivityForResult(intent,0)
            }
            R.id.rl_my_order->{//我的订单
                val intent = Intent(mContext, MyOrderActivity::class.java)
                startActivity(intent)
            }
            R.id.rl_wait_pay->{//代付款
                val intent = Intent(mContext, MyOrderActivity::class.java)
                intent.putExtra("index",1)
                startActivity(intent)
            }
            R.id.rl_wait_send->{//代发货
                val intent = Intent(mContext, MyOrderActivity::class.java)
                intent.putExtra("index",2)
                startActivity(intent)
            }
            R.id.rl_wait_receive->{//代收货
                val intent = Intent(mContext, MyOrderActivity::class.java)
                intent.putExtra("index",3)
                startActivity(intent)
            }
            R.id.rl_complete->{//已完成
                val intent = Intent(mContext, MyOrderActivity::class.java)
                intent.putExtra("index",4)
                startActivity(intent)
            }
        }
    }

    override fun onGetUserInfoSuccess(data: UserInfoEntity) {
        mActivity.hideLoadingDialog()
        mRootView.sw_mine.isRefreshing=false
        mHeaderView.iv_avatar.loadImg(mContext,API.HOST+data.avatar,R.mipmap.face_img)
        if (!TextUtils.isEmpty(data.nickname)){
            mHeaderView.tv_name.text=data.nickname
        }else{
            mHeaderView.tv_name.text=data.username
        }
        mHeaderView.tv_level.text= data.vip_name
        if ("1"==data.sex){
            mHeaderView.iv_sex.visibility=View.VISIBLE
            mHeaderView.iv_sex.setImageResource(R.mipmap.my_btn_boys)
        }else if ("2"==data.sex){
            mHeaderView.iv_sex.visibility=View.VISIBLE
            mHeaderView.iv_sex.setImageResource(R.mipmap.my_btn_girls)
        }else{
            mHeaderView.iv_sex.visibility=View.VISIBLE
            mHeaderView.iv_sex.setImageResource(R.mipmap.wenhao)
        }
    }

    override fun onGetUserInfoFailure(err: String) {
        mActivity.hideLoadingDialog()
        mRootView.sw_mine.isRefreshing=false
        toast(err)
    }

    override fun onGetOrderStatusSuccess(data: OrderStatusEntity) {
        if ("0"==data.unpay){//代付款红点
            mHeaderView.tv_red_pay.visibility=View.GONE
        }else{
            mHeaderView.tv_red_pay.visibility=View.VISIBLE
        }
        if ("0"==data.wait_send){//代发货红点
            mHeaderView.tv_red_wait_send.visibility=View.GONE
        }else{
            mHeaderView.tv_red_wait_send.visibility=View.VISIBLE
        }
        if ("0"==data.sending){//代收货红点
            mHeaderView.tv_red_wait_receive.visibility=View.GONE
        }else{
            mHeaderView.tv_red_wait_receive.visibility=View.VISIBLE
        }
    }

    override fun onGetOrderStatusFailure(err: String) {
        toast(err)
    }


}