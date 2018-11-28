package com.micropole.sxwine.module.home

import android.os.Bundle
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.mvpframe.BaseMvpActivity
import com.micropole.sxwine.R
import com.micropole.sxwine.adapter.ClassifyAdapter
import com.micropole.sxwine.base.hideLoadingDialog
import com.micropole.sxwine.base.initToolBar
import com.micropole.sxwine.base.showLoadingDialog
import com.micropole.sxwine.base.toast
import com.micropole.sxwine.bean.ClassifyBean
import com.micropole.sxwine.module.home.mvp.contract.MoreClassifyContract
import com.micropole.sxwine.module.home.mvp.presenter.MoreClassifyPresenter
import kotlinx.android.synthetic.main.activity_more_classify.*

class MoreClassifyActivity : BaseMvpActivity<MoreClassifyContract.Model, MoreClassifyContract.View, MoreClassifyPresenter>(), MoreClassifyContract.View {

    private lateinit var mClassifyAdapter: ClassifyAdapter
    private lateinit var mClassifyList: ArrayList<ClassifyBean>
    private var mPage = 1

    override fun createPresenter(): MoreClassifyPresenter = MoreClassifyPresenter()

    override fun getLayoutId(): Int = R.layout.activity_more_classify

    override fun initView() {
        initToolBar(getText(R.string.all_classify) as String)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        mClassifyList = ArrayList()
        mClassifyAdapter = ClassifyAdapter(R.layout.item_rcv_more_classify, mClassifyList)
        mClassifyAdapter.setOnLoadMoreListener({
            mPresenter.getClassify((++mPage).toString())
        }, rcv_classify)
        mClassifyAdapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener({ _, _, position ->
            val bean = mClassifyList[position]
            val bundle = Bundle()
            bundle.putString("title", bean.name)
            bundle.putString("category", bean.category_id.toString())
            startActivity(GoodsActivity::class.java, bundle)
        })
        rcv_classify.adapter = mClassifyAdapter
    }

    override fun initListener() {
    }

    override fun initData() {
        showLoadingDialog()
        mPresenter.getClassify(mPage.toString())
    }

    override fun onSuccess(result: ArrayList<ClassifyBean>, msg: String) {
        if (mPage > 1) {
            if (result.size > 0) {
                mClassifyList.addAll(result)
                mClassifyAdapter.setNewData(mClassifyList)
                mClassifyAdapter.loadMoreComplete()
            } else {
                mPage--
                mClassifyAdapter.loadMoreEnd()
            }
        } else {
            mClassifyList.clear()
            mClassifyList.addAll(result)
            mClassifyAdapter.setNewData(mClassifyList)
        }
        hideLoadingDialog()
    }

    override fun onFailure(err: String) {
        if (mPage > 1) {
            mClassifyAdapter.loadMoreEnd()
            mPage--
        }
        toast(err)
        hideLoadingDialog()
    }

}
