package com.micropole.sxwine.module.personal.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.micropole.sxwine.R
import com.micropole.sxwine.module.personal.Bean.WithdrawListEntity

/**
 * Created by JohnnyH on 2018/6/14.
 */
class WithdrawListTextAdapter(layoutResId:Int,data:List<WithdrawListEntity.RecordBean>?) : BaseQuickAdapter<WithdrawListEntity.RecordBean,BaseViewHolder>(layoutResId,data) {

    override fun convert(helper: BaseViewHolder?, item: WithdrawListEntity.RecordBean?) {
        helper!!.setText(R.id.tv_price,item!!.bank_name)
        helper.setText(R.id.tv_title,item!!.amount)
    }
}