package com.micropole.sxwine.module.personal.adapter

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.micropole.sxwine.R
import com.micropole.sxwine.module.personal.Bean.WithdrawListEntity

/**
 * Created by JohnnyH on 2018/6/14.
 */
class WithdrawListAdapter(resLayoutId:Int,data:List<WithdrawListEntity>?) : BaseQuickAdapter<WithdrawListEntity,BaseViewHolder>(resLayoutId,data) {
    override fun convert(helper: BaseViewHolder?, item: WithdrawListEntity?) {
        helper!!.setText(R.id.tv_time,item!!.date)
        val rec_text = helper.getView<RecyclerView>(R.id.rec_text)
        rec_text.layoutManager=object : LinearLayoutManager(mContext){
            override fun canScrollVertically(): Boolean {
                return false
            }
        }
        val adapter = WithdrawListTextAdapter(R.layout.item_withdraw_list_text, item.record)
        rec_text.adapter=adapter
    }
}