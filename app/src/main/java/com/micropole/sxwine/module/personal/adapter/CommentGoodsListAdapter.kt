package com.micropole.sxwine.module.personal.adapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.micropole.sxwine.R
import com.micropole.sxwine.base.loadImg
import com.micropole.sxwine.module.personal.Bean.CommentGoodsListEntity
import com.micropole.sxwine.utils.network.API

/**
 * Created by JohnnyH on 2018/6/12.
 */
class CommentGoodsListAdapter(resLayoutId : Int, data : List<CommentGoodsListEntity>?) : BaseQuickAdapter<CommentGoodsListEntity, BaseViewHolder>(resLayoutId,data) {

    override fun convert(helper: BaseViewHolder, item: CommentGoodsListEntity?) {
        val iv_goods = helper.getView<ImageView>(R.id.iv_goods)
        iv_goods.loadImg(mContext,API.HOST+item!!.goods[0].cover_img)
        helper.setText(R.id.tv_goods_name,item.goods[0].goods_name)
        helper.addOnClickListener(R.id.btn_comment)
    }
}