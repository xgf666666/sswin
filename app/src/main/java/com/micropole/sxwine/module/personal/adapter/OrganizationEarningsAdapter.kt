package com.micropole.sxwine.module.personal.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.micropole.sxwine.R
import com.micropole.sxwine.module.personal.Bean.OrganizationEarningsEntity

/**
 * Created by JohnnyH on 2018/6/14.
 */
class OrganizationEarningsAdapter(resLayoutId:Int,data:List<OrganizationEarningsEntity>?) : BaseQuickAdapter<OrganizationEarningsEntity,BaseViewHolder>(resLayoutId,data) {
    override fun convert(helper: BaseViewHolder?, item: OrganizationEarningsEntity?) {
        helper!!.setText(R.id.tv_time,item!!.created_at)
        helper!!.setText(R.id.tv_price,item!!.commission_amount)

    }
}