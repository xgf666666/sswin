package com.micropole.sxwine.module.personal.adapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.micropole.sxwine.R
import com.micropole.sxwine.module.personal.Bean.MineItemEntity

/**
 * Created by JohnnyH on 2018/6/8.
 */
class MineAdapter(resLayoutId: Int, data: List<MineItemEntity>?) : BaseQuickAdapter<MineItemEntity, BaseViewHolder>(resLayoutId, data) {

    override fun convert(helper: BaseViewHolder?, item: MineItemEntity?) {
        helper!!.getView<ImageView>(R.id.iv_item_mine).setImageResource(item!!.icon)
        helper.setText(R.id.tv_item_name,item.name)
    }
}