package com.micropole.sxwine.module.personal

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.example.mvpframe.BaseMvpActivity
import com.micropole.sxwine.R
import com.micropole.sxwine.base.hideLoadingDialog
import com.micropole.sxwine.base.initToolBar
import com.micropole.sxwine.base.showLoadingDialog
import com.micropole.sxwine.base.toast
import com.micropole.sxwine.common.Constants
import com.micropole.sxwine.module.personal.Bean.CommentGoodsListEntity
import com.micropole.sxwine.module.personal.adapter.CommentGoodsListAdapter
import com.micropole.sxwine.module.personal.mvp.contract.CommentGoodsListContract
import com.micropole.sxwine.module.personal.mvp.presenter.CommentGoodsListPresenter
import kotlinx.android.synthetic.main.activity_comment_goods_list.*
import kotlinx.android.synthetic.main.item_toolbar.*
import kotlinx.android.synthetic.main.layout_error_view.*

/**
 * Created by JohnnyH on 2018/9/7.
 * 评价商品列表
 */
class CommentGoodsListActivity : BaseMvpActivity<CommentGoodsListContract.Model,CommentGoodsListContract.View,CommentGoodsListPresenter>(),CommentGoodsListContract.View{

    private lateinit var mAdapter : CommentGoodsListAdapter
    private lateinit var mOrder_id : String

    override fun createPresenter(): CommentGoodsListPresenter = CommentGoodsListPresenter()

    override fun getLayoutId(): Int = R.layout.activity_comment_goods_list

    override fun initView() {
        initToolBar(getString(R.string.tv_btn_comment_list))
        toolbar.setNavigationOnClickListener { finish() }

        recyclerView.layoutManager=LinearLayoutManager(mContext)
        mAdapter= CommentGoodsListAdapter(R.layout.item_comment_goods_list,null)
        recyclerView.adapter=mAdapter
    }

    override fun initData() {
        mOrder_id=intent.getStringExtra("order_id")
        showLoadingDialog()
        mPresenter.loadData(mOrder_id)
    }

    override fun initListener() {
        view_error.setOnClickListener {
            view_error.visibility=View.GONE
            showLoadingDialog()
            mPresenter.loadData(mOrder_id)
        }

        recyclerView.addOnItemTouchListener(object : OnItemChildClickListener(){
            override fun onSimpleItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                val intent = Intent(mContext, OrderCommentActivity::class.java)
                intent.putExtra("order_id",mOrder_id)
                intent.putExtra("goods_id",mAdapter.data[position].goods_id)
                intent.putExtra("goods_img",mAdapter.data[position].goods[0].cover_img)
                startActivityForResult(intent,0)
            }

        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==0&&Constants.COMMENT_RESULT_CODE==resultCode){
            finish()
        }
    }

    override fun setData(data: MutableList<CommentGoodsListEntity>?) {
        hideLoadingDialog()
        mAdapter.setNewData(data)
    }

    override fun onDataFailure(err: String?) {
        hideLoadingDialog()
        toast(err!!)
        view_error.visibility= View.VISIBLE

    }
}