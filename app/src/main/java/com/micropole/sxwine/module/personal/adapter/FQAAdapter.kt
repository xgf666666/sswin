package com.micropole.sxwine.module.personal.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.micropole.sxwine.R
import com.micropole.sxwine.module.personal.Bean.FQAEntity

/**
 * Created by JohnnyH on 2018/6/12.
 */
class FQAAdapter(resLayoutId : Int,data : List<FQAEntity>?) : BaseQuickAdapter<FQAEntity,BaseViewHolder>(resLayoutId,data) {

    override fun convert(helper: BaseViewHolder?, item: FQAEntity?) {
        helper!!.setText(R.id.tv_title,item!!.question)
    }
}