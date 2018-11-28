package com.micropole.sxwine.module.personal

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.example.mvpframe.BaseMvpActivity
import com.micropole.sxwine.R
import com.micropole.sxwine.base.hideLoadingDialog
import com.micropole.sxwine.base.initToolBar
import com.micropole.sxwine.base.showLoadingDialog
import com.micropole.sxwine.base.toast
import com.micropole.sxwine.module.personal.Bean.MyWalletEntity
import com.micropole.sxwine.module.personal.Bean.MyWalletItemEntity
import com.micropole.sxwine.module.personal.adapter.MyWalletAdapter
import com.micropole.sxwine.module.personal.mvp.contract.MyWalletContract
import com.micropole.sxwine.module.personal.mvp.presenter.MyWalletPresenter
import kotlinx.android.synthetic.main.activity_my_wallet.*
import kotlinx.android.synthetic.main.item_toolbar.*

/**
 * Created by JohnnyH on 2018/6/13.
 * 我的钱包
 */
class MyWalletActivity : BaseMvpActivity<MyWalletContract.Model,MyWalletContract.View,MyWalletPresenter>(),MyWalletContract.View, View.OnClickListener {

    private lateinit var mAdapter : MyWalletAdapter

    override fun createPresenter(): MyWalletPresenter = MyWalletPresenter()
    override fun getLayoutId(): Int = R.layout.activity_my_wallet

    override fun initView() {
        initToolBar(getString(R.string.my_wallet))
        toolbar.setNavigationOnClickListener { finish() }
        recyclerView.layoutManager=LinearLayoutManager(mContext)
        mAdapter=MyWalletAdapter(R.layout.item_my_wallet,null)
        recyclerView.adapter=mAdapter
        iv_check_all_price.setOnClickListener(this)
        ll_today_earnings.setOnClickListener(this)
        ll_yesterday_earnings.setOnClickListener(this)
        ll_accumulate_earnings.setOnClickListener(this)
        btn_earnings_withdraw.setOnClickListener(this)
        btn_details.setOnClickListener(this)
    }

    override fun initData() {
        showLoadingDialog()
        loadData()
    }

    override fun initListener() {
        recyclerView.addOnItemTouchListener(object : OnItemClickListener(){
            override fun onSimpleItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                val intent = Intent(mContext, mAdapter.data[position].clazz)
                intent.putExtra("type",mAdapter.data[position].type)
                startActivity(intent)
            }

        })
    }

    private fun loadData(){
        mPresenter.loadData()
    }

    private var isCheckTotalPrice : Boolean =false
    override fun onClick(view: View?) {
        when(view!!.id){
            R.id.iv_check_all_price->{//查看余额
                isCheckTotalPrice=!isCheckTotalPrice
                if (isCheckTotalPrice){
                    tv_total_price.text=getString(R.string.tv_total_price)+mData.user_wallet
                }else{
                    tv_total_price.text=getString(R.string.tv_total_price)+"****"
                }
            }
            R.id.ll_today_earnings->{//今天收益
                val intent = Intent(mContext, EarningsDetails1Activity::class.java)
                intent.putExtra("title",getString(R.string.today_earnings_detials))
                intent.putExtra("type","1")
                startActivity(intent)
            }
            R.id.ll_yesterday_earnings->{//昨天收益
               /* val intent = Intent(mContext, EarningsDetails1Activity::class.java)
                intent.putExtra("title",getString(R.string.yesterday_earnings_details))
                intent.putExtra("type","2")
                startActivity(intent)*/
            }
            R.id.ll_accumulate_earnings->{//累积收益
               /* val intent = Intent(mContext, EarningsDetails1Activity::class.java)
                intent.putExtra("title",getString(R.string.accumulate_earnings_details))
                intent.putExtra("type","3")
                startActivity(intent)*/
            }
            R.id.btn_earnings_withdraw->{//收益提现
                val intent = Intent(mContext, EarningsWithdrawActivity2::class.java)
                intent.putExtra("total_price",mData.user_wallet)
                startActivityForResult(intent,0)
            }
            R.id.btn_details->{//提现明细
                val intent = Intent(mContext, WithdrawListActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        loadData()
    }
    private lateinit var mData : MyWalletEntity
    override fun setData(data : MyWalletEntity?) {
        hideLoadingDialog()
        mData=data!!
        tv_today_earnings_price.text=data!!.today_commission
        tv_yesterday_price.text="RMB "+data!!.yesterday_commission
        tv_accumulate_price.text="RMB "+data.commission_total
        tv_total_price.text=getString(R.string.tv_total_price)+"****"
        mAdapter.setNewData(createList(data))
    }

    override fun onDataFailure(err: String?) {
        hideLoadingDialog()
        toast(err!!)
    }

    private fun createList(data : MyWalletEntity) : List<MyWalletItemEntity>{
        var itemList :ArrayList<MyWalletItemEntity> = ArrayList()
        itemList.add(MyWalletItemEntity("1",getString(R.string.tv_year_bonus),data.directly_amount_total,OrganizationEarningsActivity::class.java))
        itemList.add(MyWalletItemEntity("2",getString(R.string.tv_quarter_bonus),data.organization_amount_total,OrganizationEarningsActivity::class.java))
        itemList.add(MyWalletItemEntity("3",getString(R.string.tv_organization_bonus),data.consume_amount_total,OrganizationEarningsActivity::class.java))
        return itemList
    }

}