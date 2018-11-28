package com.micropole.sxwine.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import android.widget.RatingBar
import com.blankj.utilcode.util.ActivityUtils.startActivity
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.micropole.sxwine.R
import com.micropole.sxwine.base.loadImg
import com.micropole.sxwine.bean.GoodDetailBean
import com.micropole.sxwine.module.home.CommendListActivity
import com.micropole.sxwine.utils.ViewPagerActivity
import com.micropole.sxwine.utils.network.API
import kotlinx.android.synthetic.main.rcv_comment.view.*

/**
 * Description:
 * Created by DarkHorse on 2018/9/7.
 */
class CommendAdapter(private val context: Context, layoutId: Int) : BaseQuickAdapter<GoodDetailBean.Comment, BaseViewHolder>(layoutId) {

    override fun convert(helper: BaseViewHolder, item: GoodDetailBean.Comment) {
        helper.getView<ImageView>(R.id.iv_head).loadImg(context, API.HOST + item.avatar)
        helper.setText(R.id.tv_name, item.nickname)
        helper.setText(R.id.tv_content, item.content)
        val mRecyclerView = helper.getView<RecyclerView>(R.id.rcv_pic)
        val adapter = PictureAdapter(context, R.layout.rcv_commend_pic)
        adapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
            val intent = Intent(mContext, ViewPagerActivity::class.java).apply {
                putExtra("imgUrl_index", position)
                putExtra("imgUrls", item.photos.toTypedArray())
            }
            startActivity(intent)
        }
        helper.getView<RatingBar>(R.id.rb_rate).rating = item.score.toFloat()
        mRecyclerView.adapter = adapter
        adapter.setNewData(item.photos)
    }

}
