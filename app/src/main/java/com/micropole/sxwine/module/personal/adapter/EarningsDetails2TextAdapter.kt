package com.micropole.sxwine.module.personal.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.micropole.sxwine.R
import com.micropole.sxwine.module.personal.Bean.EarningsDetails2Entity

/**
 * Created by JohnnyH on 2018/6/13.
 */
class EarningsDetails2TextAdapter(resLayoutId:Int,data:List<EarningsDetails2Entity.RecordBean>?) : BaseQuickAdapter<EarningsDetails2Entity.RecordBean,BaseViewHolder>(resLayoutId,data) {
    override fun convert(helper: BaseViewHolder?, item: EarningsDetails2Entity.RecordBean?) {
        helper!!.setText(R.id.tv_title,item!!.type)
        helper.setText(R.id.tv_price,item.bonus_amount)
    }
}