package com.micropole.sxwine.module.personal.adapter

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.micropole.sxwine.R
import com.micropole.sxwine.module.personal.Bean.EarningsDetails2Entity

/**
 * Created by JohnnyH on 2018/6/13.
 */
class EarningsDetails2TimeAdapter(resLayoutId:Int,data:List<EarningsDetails2Entity>?) : BaseQuickAdapter<EarningsDetails2Entity,BaseViewHolder>(resLayoutId,data) {
    override fun convert(helper: BaseViewHolder?, item: EarningsDetails2Entity?) {
        helper!!.setText(R.id.tv_time,item!!.date)
        val rec_text = helper.getView<RecyclerView>(R.id.rec_text)
        rec_text.layoutManager=LinearLayoutManager(mContext)
        rec_text.adapter=EarningsDetails2TextAdapter(R.layout.item_earnings_detials2_text,item.record)

    }
}