package com.micropole.sxwine.module.personal.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.micropole.sxwine.R
import com.micropole.sxwine.module.personal.Bean.MyWalletItemEntity

/**
 * Created by JohnnyH on 2018/6/13.
 */
class MyWalletAdapter(resLayoutId:Int,data:List<MyWalletItemEntity>?) : BaseQuickAdapter<MyWalletItemEntity,BaseViewHolder>(resLayoutId,data) {
    override fun convert(helper: BaseViewHolder?, item: MyWalletItemEntity?) {
        helper!!.setText(R.id.tv_title,item!!.title)
        helper.setText(R.id.tv_price,item.price)
    }
}