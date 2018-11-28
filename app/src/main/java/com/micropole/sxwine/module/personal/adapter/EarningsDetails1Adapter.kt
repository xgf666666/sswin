package com.micropole.sxwine.module.personal.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.micropole.sxwine.R
import com.micropole.sxwine.module.personal.Bean.EarningsDetails1ItemEnitity

/**
 * Created by JohnnyH on 2018/6/13.
 */
class EarningsDetails1Adapter(resLayoutId: Int,data : List<EarningsDetails1ItemEnitity>?) : BaseQuickAdapter<EarningsDetails1ItemEnitity,BaseViewHolder>(resLayoutId,data) {
    override fun convert(helper: BaseViewHolder?, item: EarningsDetails1ItemEnitity?) {
        helper!!.setText(R.id.tv_title,item!!.title)
        helper.setText(R.id.tv_price,item.price)
    }

}